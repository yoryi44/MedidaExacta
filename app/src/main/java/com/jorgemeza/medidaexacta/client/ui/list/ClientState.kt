package com.jorgemeza.medidaexacta.client.ui.list

import com.jorgemeza.medidaexacta.client.domain.model.ClientModel

data class ClientState(
    val searchQuery: String = "",
    val clients: List<ClientModel> = emptyList(),
    val idClientDelete: String? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)
