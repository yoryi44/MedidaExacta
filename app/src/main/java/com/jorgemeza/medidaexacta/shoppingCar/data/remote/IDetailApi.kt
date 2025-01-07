package com.jorgemeza.medidaexacta.shoppingCar.data.local

import com.jorgemeza.data.api.Api.DETAIL_DELETE_URL
import com.jorgemeza.data.api.Api.DETAIL_URL
import com.jorgemeza.medidaexacta.shoppingCar.data.remote.dto.DetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface IDetailApi {

    @GET(com.jorgemeza.data.api.Api.DETAIL_URL)
    suspend fun getAllQuotationDetail(): DetailResponse

    @PATCH(com.jorgemeza.data.api.Api.DETAIL_URL)
    suspend fun inserDetail(@Body detail: DetailResponse)

    @DELETE(com.jorgemeza.data.api.Api.DETAIL_DELETE_URL)
    suspend fun deleteDetailById(@Path("detailId", encoded = true) detailId: String)
}