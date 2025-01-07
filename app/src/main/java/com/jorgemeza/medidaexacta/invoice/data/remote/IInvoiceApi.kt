package com.jorgemeza.medidaexacta.invoice.data.remote

import com.jorgemeza.data.api.Api.INVOICE_DELETE_URL
import com.jorgemeza.data.api.Api.INVOICE_URL
import com.jorgemeza.medidaexacta.invoice.data.remote.dto.InvoiceResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface IInvoiceApi {

    @GET(com.jorgemeza.data.api.Api.INVOICE_URL)
    suspend fun getAllInvocie(): InvoiceResponse

    @PATCH(com.jorgemeza.data.api.Api.INVOICE_URL)
    suspend fun inserInvocie(@Body habit: InvoiceResponse)

    @DELETE(com.jorgemeza.data.api.Api.INVOICE_DELETE_URL)
    suspend fun deleteInvocieById(@Path("invoiceId", encoded = true) invoiceId: String)

}