package com.jorgemeza.medidaexacta.Client.domain.repository

import com.jorgemeza.medidaexacta.navigation.Client

interface IClientRepository {

    fun getAllClient(): List<Client>
    fun getClientById(id: String): Client?
    fun addClient(client: Client): Boolean
    fun updateClient(client: Client): Boolean
    fun deleteClient(id: String): Boolean

}