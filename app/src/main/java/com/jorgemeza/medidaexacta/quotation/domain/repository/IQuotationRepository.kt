package com.jorgemeza.medidaexacta.quotation.domain.repository

import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel
import com.jorgemeza.medidaexacta.quotation.domain.model.QuotationModel
import kotlinx.coroutines.flow.Flow

interface IQuotationRepository {

    suspend fun getAllQuotation(): Flow<List<QuotationModel>>
    suspend fun getAllQuotationMain(): Boolean
    suspend fun getQuotationById(id: String): QuotationModel
    suspend fun addQuotation(quotation: QuotationModel)
    suspend fun getQuotationConsecutive(): String
    suspend fun deleteQuotation(id: String): Result<Unit>
    fun getQuotationBySearch(string: String) : List<QuotationModel>

}