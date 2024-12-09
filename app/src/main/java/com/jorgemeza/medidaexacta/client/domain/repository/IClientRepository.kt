package com.jorgemeza.medidaexacta.client.domain.repository

import com.jorgemeza.medidaexacta.client.domain.model.ClientModel
import kotlinx.coroutines.flow.Flow

interface IClientRepository {

    suspend fun getAllClient(): Flow<List<ClientModel>>
    suspend fun getClientById(id: String): ClientModel
    suspend fun getClientBySearch(search: String): List<ClientModel>
    suspend fun addClient(client: ClientModel)
    suspend fun deleteClient(id: String): Result<Unit>
    suspend fun syncClient()

}