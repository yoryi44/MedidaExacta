package com.jorgemeza.medidaexacta.invoice.data.mapper

import com.jorgemeza.medidaexacta.invoice.data.local.entity.InvoiceEntity
import com.jorgemeza.medidaexacta.invoice.data.local.entity.InvoiceSyncEntity
import com.jorgemeza.medidaexacta.invoice.data.remote.dto.InvoiceDto
import com.jorgemeza.medidaexacta.invoice.data.remote.dto.InvoiceResponse
import com.jorgemeza.medidaexacta.invoice.domain.model.InvoiceModel


fun InvoiceResponse.toDomain(): List<InvoiceModel> {
    return this.map {
        InvoiceModel(
            id = it.key,
            invoiceNumber = it.value.invoiceNumber,
            quotation = it.value.quotation,
            client = it.value.client,
            date = it.value.date
        )
    }
}

fun InvoiceModel.toDto(): InvoiceResponse {
    val dto = InvoiceDto(
        invoiceNumber = this.invoiceNumber,
        quotation = this.quotation,
        client = this.client,
        date = this.date,
    )
    return mapOf(id to dto)
}

fun InvoiceEntity.toDomain(): InvoiceModel {
    return InvoiceModel(
        id = this.id,
        quotation = this.quotation,
        client = this.client,
        invoiceNumber = this.invoiceNumber,
        date = this.date
    )
}

fun InvoiceModel.toEntity(): InvoiceEntity {
    return InvoiceEntity(
        id = this.id,
        quotation = this.quotation,
        client = this.client,
        invoiceNumber = this.invoiceNumber,
        date = this.date
    )
}

fun InvoiceModel.toSyncEntity(): InvoiceSyncEntity {
    return InvoiceSyncEntity(
        id = this.id
    )

}