package com.jorgemeza.medidaexacta.invoice.data.remote.dto

import com.google.gson.annotations.SerializedName

data class InvoiceDto (
    @SerializedName("invoice_number") val invoiceNumber : String,
    @SerializedName("quotation") val quotation : String,
    @SerializedName("client") val client : String,
    @SerializedName("date") val date : String
)