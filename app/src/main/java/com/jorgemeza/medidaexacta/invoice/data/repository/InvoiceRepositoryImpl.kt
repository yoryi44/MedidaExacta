package com.jorgemeza.medidaexacta.invoice.data.repository

import com.jorgemeza.medidaexacta.client.data.mapper.toDomain
import com.jorgemeza.medidaexacta.client.data.mapper.toEntity
import com.jorgemeza.medidaexacta.client.domain.model.ClientModel
import com.jorgemeza.medidaexacta.core.util.resultOf
import com.jorgemeza.medidaexacta.invoice.data.local.InvoiceDao
import com.jorgemeza.medidaexacta.invoice.data.mapper.toDomain
import com.jorgemeza.medidaexacta.invoice.data.mapper.toEntity
import com.jorgemeza.medidaexacta.invoice.data.remote.IInvoiceApi
import com.jorgemeza.medidaexacta.invoice.domain.model.InvoiceModel
import com.jorgemeza.medidaexacta.invoice.domain.repository.IInvoiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class InvoiceRepositoryImpl(
    private val invoiceDao: InvoiceDao,
    private val invoiceApi: IInvoiceApi
) : IInvoiceRepository {

    override suspend fun getAllInvoice(): Flow<List<InvoiceModel>> {

        val localFlow = invoiceDao.getAllInvocie().map { invoice -> invoice.map { it.toDomain() } }

        val apiFlow = getInvoiceFormApi()

        return combine(localFlow, apiFlow) { db, api -> db }
    }

    private fun getInvoiceFormApi(): Flow<List<InvoiceModel>> {
        return flow {

            resultOf {
                val response = invoiceApi.getAllInvocie().toDomain()
                insertInvoice(response)
            }

            emit(emptyList<InvoiceModel>())

        }.onStart {
            emit(emptyList())
        }
    }

    override suspend fun getInvoiceById(id: String): InvoiceModel {
        TODO("Not yet implemented")
    }

    override suspend fun getInvoiceBySearch(search: String): List<InvoiceModel> {
        TODO("Not yet implemented")
    }

    override suspend fun addInvoice(invoice: InvoiceModel) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteInvoice(id: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun syncInvoice() {
        TODO("Not yet implemented")
    }

    private suspend fun insertInvoice(invoice: List<InvoiceModel>) {
        invoice.forEach {
            invoiceDao.insertInvoice(it.toEntity())
        }
    }
}