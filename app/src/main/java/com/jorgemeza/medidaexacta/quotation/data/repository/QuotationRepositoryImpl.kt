package com.jorgemeza.medidaexacta.quotation.data.repository

import com.jorgemeza.medidaexacta.client.data.local.ClientDao
import com.jorgemeza.medidaexacta.core.util.resultOf
import com.jorgemeza.medidaexacta.quotation.data.local.QuotationDao
import com.jorgemeza.medidaexacta.quotation.data.local.entity.QuotationDetailEntity
import com.jorgemeza.medidaexacta.quotation.data.mapper.toDomain
import com.jorgemeza.medidaexacta.quotation.data.mapper.toDto
import com.jorgemeza.medidaexacta.quotation.data.mapper.toEntity
import com.jorgemeza.medidaexacta.quotation.data.mapper.toSyncEntity
import com.jorgemeza.medidaexacta.quotation.data.remote.IQuotationApi
import com.jorgemeza.medidaexacta.quotation.data.remote.dto.QuotationResponse
import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel
import com.jorgemeza.medidaexacta.quotation.domain.model.QuotationModel
import com.jorgemeza.medidaexacta.quotation.domain.repository.IQuotationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class QuotationRepositoryImpl(
    private val quotationApi: IQuotationApi,
    private val clientDao: ClientDao,
    private val quotationDao: QuotationDao
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

    override suspend fun getQuotationDetailById(id: String): List<DetailModel> {
        return quotationDao.getQuotationDetailById(id).map {
            it.toDomain()
        }
    }

    override suspend fun getQuotationDetailProductById(id: String): DetailModel {
        return quotationDao.getQuotationDetailProductById(id).toDomain()
    }

    private fun getQuotationFormApi(): Flow<List<QuotationModel>> {
        return flow {

            resultOf {
                var response = quotationApi.getAllQuotation()
                insertQuotation(response.toDomain())
                insertDetail(response)
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

    private suspend fun insertDetail(quotations: QuotationResponse) {
        quotations.forEach {
            val quotationKey = it.key
            it.value.products.forEach { detail ->
                val entity : QuotationDetailEntity = detail.value.toEntity(quotationKey,detail.key)
                quotationDao.insertQuotationDetail(entity)
            }
        }
    }
}