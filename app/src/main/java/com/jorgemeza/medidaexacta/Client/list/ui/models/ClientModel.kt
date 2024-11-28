package com.jorgemeza.medidaexacta.Client.list.ui.models

data class ClientModel (
    val id : String,
    val address : String,
    val postalCode : Int,
    val name : String,
    val phone : String,
    val cif : String?,
    val email : String?
)