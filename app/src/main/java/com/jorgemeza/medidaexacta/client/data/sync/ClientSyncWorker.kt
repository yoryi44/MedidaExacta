package com.jorgemeza.medidaexacta.client.data.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.home_data.remote.util.resultOf
import com.jorgemeza.medidaexacta.client.data.local.ClientDao
import com.jorgemeza.medidaexacta.client.data.local.entity.ClientSyncEntity
import com.jorgemeza.medidaexacta.client.data.mapper.toDomain
import com.jorgemeza.medidaexacta.client.data.mapper.toDto
import com.jorgemeza.medidaexacta.client.data.remote.IClientApi
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope

@HiltWorker
class ClientSyncWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val api: IClientApi,
    private val dao: ClientDao
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        if (runAttemptCount >= 3) {
            return Result.failure()
        }

        val items = dao.getAllClientSync()

        return try {
            supervisorScope {
                val jobs = items.map { items -> async { sync(items) } }
                jobs.awaitAll()
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private suspend fun sync(entity: ClientSyncEntity) {
        val client = dao.getClientById(entity.id).toDomain().toDto()
        resultOf {
            api.inserClient(client)
        }.onSuccess {
            dao.deleteHabitSync(entity)
        }.onFailure {
            throw it
        }
    }
}