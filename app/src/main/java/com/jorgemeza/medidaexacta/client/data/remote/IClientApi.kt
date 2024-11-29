package com.jorgemeza.medidaexacta.client.data.remote

import com.jorgemeza.medidaexacta.client.data.remote.dto.ClientResponse
import com.jorgemeza.medidaexacta.core.api.Api.CLIENT_DELETE_URL
import com.jorgemeza.medidaexacta.core.api.Api.CLIENT_URL
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface IClientApi {

    @GET(CLIENT_URL)
    suspend fun getAllClient(): ClientResponse

    @PATCH(CLIENT_URL)
    suspend fun inserClient(@Body habit: ClientResponse)

    @DELETE(CLIENT_DELETE_URL)
    suspend fun deleteClientById(@Path("clientId", encoded = true) clientId: String)

}