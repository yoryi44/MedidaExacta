package com.jorgemeza.medidaexacta.Client.ui

sealed interface ClientEvent {
    data class OnSearchQueryChange(val query: String) : ClientEvent
}