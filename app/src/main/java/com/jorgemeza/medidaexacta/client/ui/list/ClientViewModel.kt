package com.jorgemeza.medidaexacta.client.ui.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgemeza.medidaexacta.client.domain.usecase.DeleteClientUseCase
import com.jorgemeza.medidaexacta.client.domain.usecase.GetAllClientUseCase
import com.jorgemeza.medidaexacta.client.domain.usecase.GetClientBySearchUseCase
import com.jorgemeza.medidaexacta.client.domain.usecase.SyncClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ClientViewModel @Inject constructor(
    private val getAllClientUseCase: GetAllClientUseCase,
    private val deleteClientUseCase: DeleteClientUseCase,
    private val getClientBySearchUseCase: GetClientBySearchUseCase,
    private val syncClientUseCase: SyncClientUseCase
) : ViewModel() {

    var state by mutableStateOf(ClientState())
        private set

    private var clientsJob : Job? = null

    init {
        getAllClient()

        viewModelScope.launch{
            syncClientUseCase()
        }
    }

    fun onEvent(event: ClientEvent) {
        when (event) {
            is ClientEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
            }
            ClientEvent.OnConfirmDialog -> {
                deleteClient()
            }
            ClientEvent.OnDismissDialog -> {
                state = state.copy(
                    idClientDelete = null,
                    error = null,
                )
            }

            is ClientEvent.OnDeleteClient -> {
                state = state.copy(idClientDelete = event.clientId)
            }

            ClientEvent.OnSearchClient -> {
                viewModelScope.launch{
                    state = state.copy(
                        clients = getClientBySearchUseCase(state.searchQuery)
                    )
                }
            }
        }
    }

    private fun deleteClient() {
        viewModelScope.launch {
            deleteClientUseCase(state.idClientDelete!!).onFailure {
                state = state.copy(
                    error = it.message,
                    idClientDelete = null,
                )
            }
            state = state.copy(idClientDelete = null)
        }
    }

    fun getAllClient() {
        clientsJob?.cancel()
        clientsJob = viewModelScope.launch {

            state = state.copy(
                isLoading = true
            )

            getAllClientUseCase().collectLatest {
                state = state.copy(
                    clients = it,
                    isLoading = false
                )
            }
        }
    }

}