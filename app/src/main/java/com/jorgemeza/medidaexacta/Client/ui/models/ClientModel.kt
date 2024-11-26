package com.jorgemeza.medidaexacta.Client.ui.models

data class ClientModel (
    val id : String,
    val direccion : String,
    val codigoPostal : Int,
    val nombre : String,
    val telefono : String,
    val cif : String?,
    val correo : String?
)