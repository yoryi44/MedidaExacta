package com.jorgemeza.medidaexacta.Client.list.ui

import com.jorgemeza.medidaexacta.Client.list.ui.models.ClientModel

data class ClientState(
    val searchQuery: String = "",
    val clients: List<ClientModel> = emptyList(),
)
