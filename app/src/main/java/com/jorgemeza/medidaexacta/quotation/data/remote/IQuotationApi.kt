package com.jorgemeza.medidaexacta.quotation.data.remote

import com.jorgemeza.medidaexacta.client.data.remote.dto.ClientResponse
import com.jorgemeza.medidaexacta.core.api.Api.CLIENT_URL
import com.jorgemeza.medidaexacta.core.api.Api.QUOTATION_URL
import com.jorgemeza.medidaexacta.quotation.data.remote.dto.QuotationResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface IQuotationApi {

    @GET(QUOTATION_URL)
    suspend fun getAllQuotation(): QuotationResponse

    @PATCH(QUOTATION_URL)
    suspend fun inserQuotation(@Body quotation: QuotationResponse)

}