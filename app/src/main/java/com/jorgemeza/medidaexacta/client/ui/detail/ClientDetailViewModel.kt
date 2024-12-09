package com.jorgemeza.medidaexacta.client.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgemeza.medidaexacta.client.domain.usecase.AddClientUseCase
import com.jorgemeza.medidaexacta.client.domain.usecase.GetClientByIdUseCase
import com.jorgemeza.medidaexacta.client.domain.model.ClientModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ClientDetailViewModel @Inject constructor(
    private val addClientUserCase: AddClientUseCase,
    private val getClientByIdUseCase: GetClientByIdUseCase
) : ViewModel() {

    var state by mutableStateOf(ClientDetailState())
        private set

    fun onEvent(event: ClientDetailEvent) {
        when (event) {
            is ClientDetailEvent.OnAddressChange -> {
                state = state.copy(address = event.address)
            }
            is ClientDetailEvent.OnCifChange -> {
                state = state.copy(cif = event.cif)
            }
            is ClientDetailEvent.OnEmailChange -> {
                state = state.copy(email = event.email)
            }
            is ClientDetailEvent.OnNameChange -> {
                state = state.copy(name = event.name)
            }
            is ClientDetailEvent.OnPhoneChange -> {
                state = state.copy(phone = event.phone)
            }
            is ClientDetailEvent.OnPostalCodeChange -> {
                state = state.copy(postalCode = event.postalCode)
            }
            ClientDetailEvent.OnSaveClient -> {
                viewModelScope.launch {

                    val client = ClientModel(
                        id = state.id ?: UUID.randomUUID().toString(),
                        address = state.address,
                        postalCode = state.postalCode,
                        name = state.name,
                        phone = state.phone,
                        cif = state.cif,
                        email = state.email
                    )

                    addClientUserCase(client)
                }
                state = state.copy(
                    isSaveSuccessful = true
                )
            }
        }
    }

    fun getClientById(clientId: String) {

        viewModelScope.launch {

            state = state.copy(
                isLoading = true
            )

            val client = getClientByIdUseCase(clientId)

            state = state.copy(
                id = client.id,
                address = client.address,
                postalCode = client.postalCode.toString(),
                name = client.name,
                phone = client.phone,
                cif = client.cif,
                email = client.email,
                isLoading = false
            )
        }

    }

}