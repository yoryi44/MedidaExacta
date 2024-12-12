package com.jorgemeza.medidaexacta.quotation.data.mapper

import com.jorgemeza.medidaexacta.shoppingCar.data.local.entity.DetailSyncEntity
import com.jorgemeza.medidaexacta.shoppingCar.data.local.entity.QuotationDetailEntity
import com.jorgemeza.medidaexacta.quotation.data.local.entity.QuotationEntity
import com.jorgemeza.medidaexacta.quotation.data.local.entity.QuotationSyncEntity
import com.jorgemeza.medidaexacta.shoppingCar.data.remote.dto.DetailDto
import com.jorgemeza.medidaexacta.quotation.data.remote.dto.QuotationDto
import com.jorgemeza.medidaexacta.quotation.data.remote.dto.QuotationResponse
import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel
import com.jorgemeza.medidaexacta.quotation.domain.model.QuotationModel
import com.jorgemeza.medidaexacta.shoppingCar.data.remote.dto.DetailResponse

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

fun DetailResponse.toDetailDomain(): List<DetailModel> {

    return this.map {
        DetailModel(
            id = it.key,
            price = it.value.price,
            amount = it.value.amount,
            product = it.value.product,
            quotation = it.value.quotation
        )
    }
}

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
        quotation = this.quotation,
        amount = this.amount,
        price = this.price,
        product = this.product
    )
}

fun DetailModel.toEntity(): QuotationDetailEntity {
    return QuotationDetailEntity(
        id = this.id,
        quotation = this.quotation,
        amount = this.amount,
        price = this.price,
        product = this.product
    )
}

fun DetailModel.toDto(): DetailResponse {
    val dto = DetailDto(
        amount = this.amount,
        price = this.price,
        product = this.product,
        quotation = this.quotation
    )

    return mapOf(this.id to dto)
}

fun DetailModel.toSyncEntity(): DetailSyncEntity {
    return DetailSyncEntity(
        id = this.id
    )
}

