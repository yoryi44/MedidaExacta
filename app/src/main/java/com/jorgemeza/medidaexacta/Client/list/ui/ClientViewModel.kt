package com.jorgemeza.medidaexacta.Client.list.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgemeza.medidaexacta.Client.list.domain.usecase.getAllClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ClientViewModel @Inject constructor(
    private val getAllClientUseCase: getAllClientUseCase
) : ViewModel() {

    var state by mutableStateOf(ClientState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(
                clients = getAllClientUseCase()
            )

        }
    }

    fun onEvent(event: ClientEvent) {
        when (event) {
            is ClientEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
            }
        }
    }
}