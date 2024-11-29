package com.jorgemeza.medidaexacta.client.ui.list

sealed interface ClientEvent {
    data class OnSearchQueryChange(val query: String) : ClientEvent
    object OnDismissDialog : ClientEvent
    object OnConfirmDialog : ClientEvent
    data class OnDeleteClient(val clientId: String) : ClientEvent
    object OnSearchClient : ClientEvent
}