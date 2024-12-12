package com.jorgemeza.medidaexacta.quotation.data.repository

import com.jorgemeza.medidaexacta.client.data.local.ClientDao
import com.jorgemeza.medidaexacta.core.util.resultOf
import com.jorgemeza.medidaexacta.quotation.data.local.QuotationDao
import com.jorgemeza.medidaexacta.quotation.data.mapper.toDomain
import com.jorgemeza.medidaexacta.quotation.data.mapper.toDetailDomain
import com.jorgemeza.medidaexacta.quotation.data.mapper.toDto
import com.jorgemeza.medidaexacta.quotation.data.mapper.toEntity
import com.jorgemeza.medidaexacta.quotation.data.mapper.toSyncEntity
import com.jorgemeza.medidaexacta.quotation.data.remote.IQuotationApi
import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel
import com.jorgemeza.medidaexacta.quotation.domain.model.QuotationModel
import com.jorgemeza.medidaexacta.quotation.domain.repository.IQuotationRepository
import com.jorgemeza.medidaexacta.shoppingCar.data.local.DetailDao
import com.jorgemeza.medidaexacta.shoppingCar.data.remote.dto.IDetailApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class QuotationRepositoryImpl(
    private val detailApi: IDetailApi,
    private val quotationApi: IQuotationApi,
    private val clientDao: ClientDao,
    private val quotationDao: QuotationDao,
    private val detailDao: DetailDao
) : IQuotationRepository {

    override suspend fun getAllQuotation(): Flow<List<QuotationModel>> {

        val localFlow = quotationDao.getAllQuotation()
            .map { quotation ->
                quotation.map {
                    it.toDomain().copy(
                        client = clientDao.getClientById(it.client).name
                    )
                }
            }

        val apiFlow = getQuotationFormApi()

        return localFlow.combine(apiFlow) { db, api -> db }
    }

    override suspend fun getAllQuotationDetail() {
        resultOf {
            var response = detailApi.getAllQuotationDetail().toDetailDomain()
            insertDetail(response)
        }
    }

    override suspend fun getQuotationById(id: String): QuotationModel {
        return quotationDao.getQuotationById(id).toDomain().let { quotation ->
            quotation.copy(
                client = clientDao.getClientById(quotation.client).name
            )
        }
    }

    override suspend fun addQuotationUseCase(quotation: QuotationModel) {
        quotationDao.insertQuotation(quotation.toEntity())
        resultOf {
            quotationApi.inserQuotation(quotation.toDto())
        }.onFailure {
            quotationDao.insertQuotationSync(quotation.toSyncEntity())
        }
    }

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

    override suspend fun getQuotationConsecutive(): String {
        return quotationDao.getQuotationConsecutive()
    }

    override suspend fun deleteQuotationDetailUseCase(id: String) : Result<Unit> {
        return resultOf {
            detailDao.deleteQuotationDetailById(id)
        }.onSuccess {
            detailDao.deleteQuotationDetailById(id)
            detailDao.deleteQuotationDetailSyncById(id)
            Result.success(Unit)
        }.onFailure {
            Result.failure<Unit>(it)
        }
    }

    override suspend fun deleteQuotationUseCase(id: String): Result<Unit> {
        return resultOf {
            quotationApi.deleteQuotationById(id)
        }.onSuccess {
            quotationDao.deleteQuotationById(id)
            quotationDao.deleteQuotationSyncById(id)
            Result.success(Unit)
        }.onFailure {
            Result.failure<Unit>(it)
        }
    }

    private fun getQuotationFormApi(): Flow<List<QuotationModel>> {
        return flow {

            resultOf {
                var response = quotationApi.getAllQuotation()
                insertQuotation(response.toDomain())
            }

            emit(emptyList<QuotationModel>())

        }.onStart {
            emit(emptyList())
        }
    }

    private suspend fun insertQuotation(quotations: List<QuotationModel>) {
        quotations.forEach {
            quotationDao.insertQuotation(it.toEntity())
        }
    }

    private suspend fun insertDetail(details: List<DetailModel>) {
        details.forEach {
            detailDao.insertQuotationDetail(it.toEntity())
        }
    }
}