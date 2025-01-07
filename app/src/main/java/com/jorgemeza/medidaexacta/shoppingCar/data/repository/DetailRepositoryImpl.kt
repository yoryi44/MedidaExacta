package com.jorgemeza.medidaexacta.shoppingCar.data.repository

import androidx.room.Transaction
import com.jorgemeza.data.util.resultOf
import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel
import com.jorgemeza.medidaexacta.shoppingCar.data.mapper.toDomain
import com.jorgemeza.medidaexacta.shoppingCar.data.mapper.toDto
import com.jorgemeza.medidaexacta.shoppingCar.data.mapper.toEntity
import com.jorgemeza.medidaexacta.shoppingCar.data.mapper.toSyncEntity
import com.jorgemeza.medidaexacta.shoppingCar.data.local.DetailDao
import com.jorgemeza.medidaexacta.shoppingCar.data.local.IDetailApi
import com.jorgemeza.medidaexacta.shoppingCar.data.mapper.toDetailDomain
import com.jorgemeza.medidaexacta.shoppingCar.domain.repository.IDetailRepository

class DetailRepositoryImpl(
    private val detailApi: IDetailApi,
    private val detailDao: DetailDao
) : IDetailRepository {

    override suspend fun addDetail(detail: DetailModel) {
        detailDao.insertDetail(detail.toEntity())
        resultOf {
            detailApi.inserDetail(detail.toDto())
        }.onFailure {
            detailDao.insertDetailSync(detail.toSyncEntity())
        }
    }

    override suspend fun getDetailById(id: String): List<DetailModel> {
        return detailDao.getDetailByQuotationId(id).map {
            it.toDomain()
        }
    }

    override suspend fun getProductById(id: String): DetailModel {
        return detailDao.getDetailProductById(id).toDomain()
    }

    @Transaction
    override suspend fun deleteDetail(id: String) : Result<Unit> {
        return resultOf {
            detailApi.deleteDetailById(id)
        }.onSuccess {
            detailDao.deleteDetailById(id)
            detailDao.deleteQuotationDetailSyncById(id)
            Result.success(Unit)
        }.onFailure {
            Result.failure<Unit>(it)
        }
    }

    override suspend fun getAllDetail() {
        resultOf {
            var response = detailApi.getAllQuotationDetail().toDetailDomain()
            insertDetail(response)
        }
    }

    private suspend fun insertDetail(details: List<DetailModel>) {
        details.forEach {
            detailDao.insertDetail(it.toEntity())
        }
    }

}