package com.jorgemeza.medidaexacta.Client.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ClientViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(ClientState())
        private set

    fun onEvent(event: ClientEvent) {
        when (event) {
            is ClientEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
            }
        }
    }
}