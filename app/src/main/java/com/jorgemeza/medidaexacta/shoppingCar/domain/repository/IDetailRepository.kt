package com.jorgemeza.medidaexacta.shoppingCar.domain.repository

import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel

interface IDetailRepository {

    suspend fun addDetail(detail: DetailModel)
    suspend fun getDetailById(id: String): List<DetailModel>
    suspend fun getProductById(id: String): DetailModel
    suspend fun deleteDetail(id: String): Result<Unit>
    suspend fun getAllDetail()

}