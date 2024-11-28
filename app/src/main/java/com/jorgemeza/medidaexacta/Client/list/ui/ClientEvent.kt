package com.jorgemeza.medidaexacta.Client.list.ui

sealed interface ClientEvent {
    data class OnSearchQueryChange(val query: String) : ClientEvent
}