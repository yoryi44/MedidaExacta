package com.jorgemeza.medidaexacta.shoppingCar.domain.repository

import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel

interface IDetailRepository {

    suspend fun addDetailUseCase(detail: DetailModel)
    suspend fun getDetailById(id: String): List<DetailModel>
    suspend fun getProductById(id: String): DetailModel
    suspend fun deleteQuotationDetailUseCase(id: String): Result<Unit>

}