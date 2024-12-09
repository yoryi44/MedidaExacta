package com.jorgemeza.medidaexacta.client.data.mapper

import com.jorgemeza.medidaexacta.client.data.local.entity.ClientEntity
import com.jorgemeza.medidaexacta.client.data.local.entity.ClientSyncEntity
import com.jorgemeza.medidaexacta.client.data.remote.dto.ClientDto
import com.jorgemeza.medidaexacta.client.data.remote.dto.ClientResponse
import com.jorgemeza.medidaexacta.client.domain.model.ClientModel

fun ClientResponse.toDomain(): List<ClientModel> {
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

fun ClientModel.toDto(): ClientResponse {
    val dto = ClientDto(
        address = this.address,
        postalCode = this.postalCode,
        name = this.name,
        cif = this.cif,
        phone = this.phone,
        email = this.email
    )

    return mapOf(id to dto)
}

fun ClientEntity.toDomain(): ClientModel {
    return ClientModel(
        id = this.id,
        address = this.address,
        postalCode = this.postalCode,
        name = this.name,
        cif = this.cif,
        phone = this.phone,
        email = this.email
    )
}

fun ClientModel.toEntity(): ClientEntity {
    return ClientEntity(
        id = this.id,
        address = this.address,
        postalCode = this.postalCode,
        name = this.name,
        cif = this.cif,
        phone = this.phone,
        email = this.email
    )
}

fun ClientModel.toSyncEntity(): ClientSyncEntity {
    return ClientSyncEntity(
        id = this.id
    )

}