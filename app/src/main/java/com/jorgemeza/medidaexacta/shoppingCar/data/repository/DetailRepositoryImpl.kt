package com.jorgemeza.medidaexacta.shoppingCar.data.repository

import com.jorgemeza.medidaexacta.core.util.resultOf
import com.jorgemeza.medidaexacta.quotation.data.mapper.toDomain
import com.jorgemeza.medidaexacta.quotation.data.mapper.toDto
import com.jorgemeza.medidaexacta.quotation.data.mapper.toEntity
import com.jorgemeza.medidaexacta.quotation.data.mapper.toSyncEntity
import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel
import com.jorgemeza.medidaexacta.shoppingCar.data.local.DetailDao
import com.jorgemeza.medidaexacta.shoppingCar.data.remote.dto.IDetailApi
import com.jorgemeza.medidaexacta.shoppingCar.domain.repository.IDetailRepository

class DetailRepositoryImpl(
    private val detailApi: IDetailApi,
    private val detailDao: DetailDao
) : IDetailRepository {

    override suspend fun addDetailUseCase(detail: DetailModel) {
        detailDao.insertQuotationDetail(detail.toEntity())
        resultOf {
            detailApi.inserDetail(detail.toDto())
        }.onFailure {
            detailDao.insertDetailSync(detail.toSyncEntity())
        }
    }

    override suspend fun getDetailById(id: String): List<DetailModel> {
        return detailDao.getQuotationDetailById(id).map {
            it.toDomain()
        }
    }

    override suspend fun getProductById(id: String): DetailModel {
        return detailDao.getQuotationDetailProductById(id).toDomain()
    }

    override suspend fun deleteQuotationDetailUseCase(id: String) : Result<Unit> {
        return resultOf {
            detailApi.deleteQuotationDetailById(id)
        }.onSuccess {
            detailDao.deleteQuotationDetailById(id)
            detailDao.deleteQuotationDetailSyncById(id)
            Result.success(Unit)
        }.onFailure {
            Result.failure<Unit>(it)
        }
    }

}