package com.jorgemeza.medidaexacta.Client.list.domain.repository

import com.jorgemeza.medidaexacta.Client.list.ui.models.ClientModel
import com.jorgemeza.medidaexacta.navigation.Client

interface IClientRepository {

    suspend fun getAllClient(): List<ClientModel>
    suspend fun getClientById(id: String): ClientModel?
    suspend fun addClient(client: ClientModel): Boolean
    suspend fun updateClient(client: ClientModel): Boolean
    suspend fun deleteClient(id: String): Boolean

}