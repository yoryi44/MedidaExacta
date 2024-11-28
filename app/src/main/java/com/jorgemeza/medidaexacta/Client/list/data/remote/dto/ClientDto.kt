package com.jorgemeza.medidaexacta.Client.list.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ClientDto(
    @SerializedName("address") val address : String,
    @SerializedName("postal_code") val postalCode : Int,
    @SerializedName("name") val name : String,
    @SerializedName("phone")val phone : String,
    @SerializedName("cif") val cif : String?,
    @SerializedName("email") val email : String?
)
