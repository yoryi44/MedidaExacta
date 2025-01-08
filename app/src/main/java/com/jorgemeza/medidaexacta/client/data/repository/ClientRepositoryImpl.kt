package com.jorgemeza.medidaexacta.client.data.repository

import androidx.room.Transaction
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.jorgemeza.medidaexacta.client.data.local.ClientDao
import com.jorgemeza.medidaexacta.client.data.mapper.toDomain
import com.jorgemeza.medidaexacta.client.data.mapper.toDto
import com.jorgemeza.medidaexacta.client.data.mapper.toEntity
import com.jorgemeza.medidaexacta.client.data.mapper.toSyncEntity
import com.jorgemeza.medidaexacta.client.data.remote.IClientApi
import com.jorgemeza.medidaexacta.client.data.sync.ClientSyncWorker
import com.jorgemeza.medidaexacta.client.domain.model.ClientModel
import com.jorgemeza.medidaexacta.client.domain.repository.IClientRepository
import com.jorgemeza.medidaexacta.core.util.resultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.time.Duration

class ClientRepositoryImpl(
    private val clientApi: IClientApi,
    private val clientDao: ClientDao,
    private val workManager: WorkManager
) : IClientRepository {

    override suspend fun getAllClient(): Flow<List<ClientModel>> {

        val localFlow = clientDao.getAllClient()
            .map { client -> client.map { it.toDomain() } }

        val apiFlow = getClientFormApi()

        return localFlow.combine(apiFlow) { db, api -> db }
    }

    override suspend fun getAllClientMain(): Boolean {

        val local = clientDao.getAllClientMain()
            .map { client -> client.toDomain() }

        val api = getClientFormApiMain()

        return local.isEmpty() && api.isEmpty()
    }

    override suspend fun getClientById(id: String): ClientModel {
        return clientDao.getClientById(id).toDomain()
    }

    override suspend fun getClientBySearch(search: String): List<ClientModel> {
        return clientDao.getClientBySeacrh(search).map { it.toDomain() }
    }

    override suspend fun addClient(client: ClientModel) {

        clientDao.insertClient(client.toEntity())
        resultOf {
            clientApi.inserClient(client.toDto())
        }.onFailure {
            clientDao.insertClientSync(client.toSyncEntity())
        }
    }

    @Transaction
    override suspend fun deleteClient(id: String): Result<Unit> {
        return runCatching {
            clientApi.deleteClientById(id)
            clientDao.deleteClientById(id)
            clientDao.deleteClientSyncById(id)
        }
    }

    override suspend fun syncClient() {
        val worker = OneTimeWorkRequestBuilder<ClientSyncWorker>().setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(5))
            .build()

        workManager.beginUniqueWork("client_client_id", ExistingWorkPolicy.REPLACE, worker)
            .enqueue()
    }

    private fun getClientFormApi(): Flow<List<ClientModel>> {
        return flow {

            resultOf {
                val response = clientApi.getAllClient().toDomain()
                insertClient(response)
            }

            emit(emptyList<ClientModel>())

        }.onStart {
            emit(emptyList())
        }
    }

    private suspend fun getClientFormApiMain(): List<ClientModel> {
        val response = clientApi.getAllClient().toDomain()
        insertClient(response)
        return response
    }

    private suspend fun insertClient(client: List<ClientModel>) {
        client.forEach {
            clientDao.insertClient(it.toEntity())
        }
    }
}