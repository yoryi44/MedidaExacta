package com.jorgemeza.medidaexacta.quotation.data.repository

import com.jorgemeza.data.util.resultOf
import com.jorgemeza.medidaexacta.quotation.data.local.QuotationDao
import com.jorgemeza.medidaexacta.quotation.data.mapper.toDomain
import com.jorgemeza.medidaexacta.quotation.data.mapper.toDto
import com.jorgemeza.medidaexacta.quotation.data.mapper.toEntity
import com.jorgemeza.medidaexacta.quotation.data.mapper.toSyncEntity
import com.jorgemeza.medidaexacta.quotation.data.remote.IQuotationApi
import com.jorgemeza.medidaexacta.quotation.domain.model.QuotationModel
import com.jorgemeza.medidaexacta.quotation.domain.repository.IQuotationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class QuotationRepositoryImpl(
    private val quotationApi: IQuotationApi,
    private val quotationDao: QuotationDao,
) : IQuotationRepository {

    override suspend fun getAllQuotation(): Flow<List<QuotationModel>> {

        val localFlow = quotationDao.getAllQuotation()
            .map { quotation ->
                quotation.map {
                    it.toDomain()
                }
            }

        val apiFlow = getQuotationFormApi()

        return localFlow.combine(apiFlow) { db, api -> db }
    }

    override suspend fun getAllQuotationMain() : Boolean {

        val local = quotationDao.getAllQuotationMain()
            .map { quotation ->
                quotation.toDomain()
            }

        val api = getQuotationFormApiMain()

        return local.isEmpty() && api.isEmpty()

    }

    override suspend fun getQuotationById(id: String): QuotationModel {
        return quotationDao.getQuotationById(id).toDomain()
    }

    override suspend fun addQuotation(quotation: QuotationModel) {
        quotationDao.insertQuotation(quotation.toEntity())
        resultOf {
            quotationApi.inserQuotation(quotation.toDto())
        }.onFailure {
            quotationDao.insertQuotationSync(quotation.toSyncEntity())
        }
    }

    override suspend fun getQuotationConsecutive(): String {
        return quotationDao.getQuotationConsecutive()
    }

    override suspend fun deleteQuotation(id: String): Result<Unit> {
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

    override fun getQuotationBySearch(search: String): List<QuotationModel> {
        return quotationDao.getQuotationBySearch(search).map { it.toDomain() }
    }

    private fun getQuotationFormApi(): Flow<List<QuotationModel>> {
        return flow {

            resultOf {
                var response = quotationApi.getAllQuotation().toDomain()
                insertQuotation(response)
            }

            emit(emptyList<QuotationModel>())

        }.onStart {
            emit(emptyList())
        }
    }

    private suspend fun getQuotationFormApiMain(): List<QuotationModel> {
        var response = quotationApi.getAllQuotation().toDomain()
        insertQuotation(response)
        return response
    }

    private suspend fun insertQuotation(quotations: List<QuotationModel>) {
        quotations.forEach {
            quotationDao.insertQuotation(it.toEntity())
        }
    }

}