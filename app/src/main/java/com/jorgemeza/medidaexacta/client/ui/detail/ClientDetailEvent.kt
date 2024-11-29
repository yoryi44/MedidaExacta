package com.jorgemeza.medidaexacta.client.ui.detail

sealed interface ClientDetailEvent {
    data class OnNameChange(val name: String) : ClientDetailEvent
    data class OnAddressChange(val address: String) : ClientDetailEvent
    data class OnPostalCodeChange(val postalCode: String) : ClientDetailEvent
    data class OnPhoneChange(val phone: String) : ClientDetailEvent
    data class OnCifChange(val cif: String) : ClientDetailEvent
    data class OnEmailChange(val email: String) : ClientDetailEvent
    object OnSaveClient : ClientDetailEvent
}