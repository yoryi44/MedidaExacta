package com.jorgemeza.medidaexacta.client.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ClientDto(
    @SerializedName("address") val address : String,
    @SerializedName("postal_code") val postalCode : String,
    @SerializedName("name") val name : String,
    @SerializedName("phone")val phone : String,
    @SerializedName("cif") val cif : String?,
    @SerializedName("email") val email : String?
)
