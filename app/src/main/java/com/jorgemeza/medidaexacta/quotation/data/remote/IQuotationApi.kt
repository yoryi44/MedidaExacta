package com.jorgemeza.medidaexacta.quotation.data.remote

import com.jorgemeza.medidaexacta.core.api.Api.QUOTATION_DELETE_URL
import com.jorgemeza.medidaexacta.core.api.Api.QUOTATION_URL
import com.jorgemeza.medidaexacta.quotation.data.remote.dto.QuotationResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface IQuotationApi {

    @GET(QUOTATION_URL)
    suspend fun getAllQuotation(): QuotationResponse

    @PATCH(QUOTATION_URL)
    suspend fun inserQuotation(@Body quotation: QuotationResponse)

    @DELETE(QUOTATION_DELETE_URL)
    suspend fun deleteQuotationById(@Path("quotationId", encoded = true) quotationId: String)

}