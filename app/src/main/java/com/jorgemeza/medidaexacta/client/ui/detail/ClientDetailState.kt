package com.jorgemeza.medidaexacta.client.ui.detail

data class ClientDetailState(
    val id : String? = null,
    val address : String = "",
    val postalCode : String = "",
    val name : String = "",
    val phone : String = "",
    val cif : String? = null,
    val email : String? = null,
    val isSaveSuccessful : Boolean = false,
    val isLoading: Boolean = false
)
