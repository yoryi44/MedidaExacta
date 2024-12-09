package com.jorgemeza.medidaexacta.quotation.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DetailDto(
    @SerializedName("amount") val amount : String,
    @SerializedName("price") val price : String,
    @SerializedName("product") val product : String,
)
