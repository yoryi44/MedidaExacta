package com.jorgemeza.medidaexacta.Client.list.data.repository

import com.jorgemeza.medidaexacta.Client.list.data.mapper.toDomain
import com.jorgemeza.medidaexacta.Client.list.data.remote.IClientApi
import com.jorgemeza.medidaexacta.Client.list.domain.repository.IClientRepository
import com.jorgemeza.medidaexacta.Client.list.ui.models.ClientModel

class ClientRepositoryImpl(
    private val clientApi: IClientApi
) : IClientRepository {

    override suspend fun getAllClient(): List<ClientModel> {
        return clientApi.getAllClient().toDomain();
    }

    override suspend fun getClientById(id: String): ClientModel? {
        TODO("Not yet implemented")
    }

    override suspend fun addClient(client: ClientModel): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateClient(client: ClientModel): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteClient(id: String): Boolean {
        TODO("Not yet implemented")
    }
}