package com.jorgemeza.medidaexacta.client.data.remote

import com.jorgemeza.medidaexacta.client.data.remote.dto.ClientResponse
import com.jorgemeza.data.api.Api.CLIENT_DELETE_URL
import com.jorgemeza.data.api.Api.CLIENT_URL
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface IClientApi {

    @GET(com.jorgemeza.data.api.Api.CLIENT_URL)
    suspend fun getAllClient(): ClientResponse

    @PATCH(com.jorgemeza.data.api.Api.CLIENT_URL)
    suspend fun inserClient(@Body habit: ClientResponse)

    @DELETE(com.jorgemeza.data.api.Api.CLIENT_DELETE_URL)
    suspend fun deleteClientById(@Path("clientId", encoded = true) clientId: String)

}