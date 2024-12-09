package com.jorgemeza.medidaexacta.client.domain.model

data class ClientModel (
    val id : String,
    val address : String,
    val postalCode : String,
    val name : String,
    val phone : String,
    val cif : String?,
    val email : String?
)