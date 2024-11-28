package com.jorgemeza.medidaexacta.Client.list.data.mapper

import com.jorgemeza.medidaexacta.Client.list.data.remote.dto.ClientResponse
import com.jorgemeza.medidaexacta.Client.list.ui.models.ClientModel

fun ClientResponse.toDomain() : List<ClientModel> {
    return this.map {
        ClientModel(
            id = it.key,
            address = it.value.address,
            postalCode = it.value.postalCode,
            name = it.value.name,
            cif = it.value.cif,
            phone = it.value.phone,
            email = it.value.email
        )
    }
}