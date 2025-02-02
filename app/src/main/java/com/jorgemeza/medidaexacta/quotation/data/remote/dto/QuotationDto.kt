package com.jorgemeza.medidaexacta.quotation.data.remote.dto

import com.google.gson.annotations.SerializedName

data class QuotationDto (
    @SerializedName("client_id") val client : String,
    @SerializedName("quotation_number") val quotationNumber : String,
    @SerializedName("price") val price : String,
    @SerializedName("date") val date : String,
    @SerializedName("observation") val observation : String?
)