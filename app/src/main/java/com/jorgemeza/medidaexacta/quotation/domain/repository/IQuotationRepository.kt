package com.jorgemeza.medidaexacta.quotation.domain.repository

import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel
import com.jorgemeza.medidaexacta.quotation.domain.model.QuotationModel
import kotlinx.coroutines.flow.Flow

interface IQuotationRepository {

    suspend fun getAllQuotation(): Flow<List<QuotationModel>>
    suspend fun getQuotationById(id: String): QuotationModel
    suspend fun addQuotationUseCase(quotation: QuotationModel)
    suspend fun getQuotationDetailById(id: String): List<DetailModel>
    suspend fun getQuotationDetailProductById(id: String): DetailModel

}