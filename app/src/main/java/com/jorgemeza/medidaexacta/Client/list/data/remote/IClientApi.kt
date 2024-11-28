package com.jorgemeza.medidaexacta.Client.list.data.remote

import com.jorgemeza.medidaexacta.Client.list.data.remote.dto.ClientResponse
import com.jorgemeza.medidaexacta.core.api.Api.CLIENT_URL
import retrofit2.http.GET

interface IClientApi {

    @GET(CLIENT_URL)
    suspend fun getAllClient(): ClientResponse

}