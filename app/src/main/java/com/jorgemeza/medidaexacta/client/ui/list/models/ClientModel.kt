package com.jorgemeza.medidaexacta.client.ui.list.models

data class ClientModel (
    val id : String,
    val address : String,
    val postalCode : String,
    val name : String,
    val phone : String,
    val cif : String?,
    val email : String?
)