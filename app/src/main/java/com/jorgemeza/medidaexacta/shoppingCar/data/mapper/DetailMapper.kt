package com.jorgemeza.medidaexacta.shoppingCar.data.mapper

import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel
import com.jorgemeza.medidaexacta.shoppingCar.data.local.entity.DetailSyncEntity
import com.jorgemeza.medidaexacta.shoppingCar.data.local.entity.QuotationDetailEntity
import com.jorgemeza.medidaexacta.shoppingCar.data.remote.dto.DetailDto
import com.jorgemeza.medidaexacta.shoppingCar.data.remote.dto.DetailResponse

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
