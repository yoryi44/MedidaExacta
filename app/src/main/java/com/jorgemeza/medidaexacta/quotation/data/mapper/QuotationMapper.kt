package com.jorgemeza.medidaexacta.quotation.data.mapper

import com.jorgemeza.medidaexacta.quotation.data.local.entity.QuotationDetailEntity
import com.jorgemeza.medidaexacta.quotation.data.local.entity.QuotationEntity
import com.jorgemeza.medidaexacta.quotation.data.local.entity.QuotationSyncEntity
import com.jorgemeza.medidaexacta.quotation.data.remote.dto.DetailDto
import com.jorgemeza.medidaexacta.quotation.data.remote.dto.DetailResponse
import com.jorgemeza.medidaexacta.quotation.data.remote.dto.QuotationDto
import com.jorgemeza.medidaexacta.quotation.data.remote.dto.QuotationResponse
import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel
import com.jorgemeza.medidaexacta.quotation.domain.model.QuotationModel

/***********************************************************
 * Quotation Mapper
 ***********************************************************/

fun QuotationResponse.toDomain(): List<QuotationModel> {

    return this.map {
        QuotationModel(
            id = it.key,
            client = it.value.client,
            quotationNumber = it.value.quotationNumber,
            price = it.value.price,
            date = it.value.date
        )
    }
}

fun QuotationModel.toEntity(): QuotationEntity {
    return QuotationEntity(
        id = this.id,
        client = this.client,
        quotationNumber = this.quotationNumber,
        price = this.price,
        date = this.date
    )
}

fun QuotationEntity.toDomain(): QuotationModel {
    return QuotationModel(
        id = this.id,
        client = this.client,
        quotationNumber = this.quotationNumber,
        price = this.price,
        date = this.date
    )
}

fun QuotationModel.toDto(): QuotationResponse {
    val dto = QuotationDto(
        client = this.client,
        quotationNumber = this.quotationNumber,
        products = emptyMap(),
        price = this.price,
        date = this.date
    )
    return mapOf(id to dto)
}

fun QuotationModel.toSyncEntity(): QuotationSyncEntity {
    return QuotationSyncEntity(
        id = this.id
    )
}

/***************************************************************
 * Quotation Detail Mapper
 ***************************************************************/

fun DetailDto.toEntity(quotationKey: String,key: String): QuotationDetailEntity {
    return QuotationDetailEntity(
        id = key,
        quotation = quotationKey,
        amount = this.amount,
        price = this.price,
        product = this.product
    )
}

fun QuotationDetailEntity.toDomain() : DetailModel {
    return DetailModel(
        id = this.id,
        amount = this.amount,
        price = this.price,
        product = this.product
    )
}

