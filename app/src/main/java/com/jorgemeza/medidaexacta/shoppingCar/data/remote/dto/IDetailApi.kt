package com.jorgemeza.medidaexacta.shoppingCar.data.remote.dto

import com.jorgemeza.medidaexacta.core.api.Api.DETAIL_DELETE_URL
import com.jorgemeza.medidaexacta.core.api.Api.DETAIL_URL
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface IDetailApi {

    @GET(DETAIL_URL)
    suspend fun getAllQuotationDetail(): DetailResponse

    @PATCH(DETAIL_URL)
    suspend fun inserDetail(@Body detail: DetailResponse)

    @DELETE(DETAIL_DELETE_URL)
    suspend fun deleteQuotationDetailById(@Path("detailId", encoded = true) detailId: String)
}