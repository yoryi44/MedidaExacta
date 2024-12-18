package com.jorgemeza.medidaexacta.invoice.domain.repository

import com.jorgemeza.medidaexacta.invoice.domain.model.InvoiceModel
import kotlinx.coroutines.flow.Flow

interface IInvoiceRepository {

    suspend fun getAllInvoice(): Flow<List<InvoiceModel>>
    suspend fun getInvoiceById(id: String): InvoiceModel
    suspend fun getInvoiceBySearch(search: String): List<InvoiceModel>
    suspend fun addInvoice(invoice: InvoiceModel)
    suspend fun deleteInvoice(id: String): Result<Unit>
    suspend fun getInvoiceConsecutive(): String
    suspend fun syncInvoice()

}