package com.jorgemeza.medidaexacta.client.data.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.jorgemeza.data.util.resultOf
import com.jorgemeza.medidaexacta.client.data.local.ClientDao
import com.jorgemeza.medidaexacta.client.data.local.entity.ClientSyncEntity
import com.jorgemeza.medidaexacta.client.data.mapper.toDomain
import com.jorgemeza.medidaexacta.client.data.mapper.toDto
import com.jorgemeza.medidaexacta.client.data.remote.IClientApi
import com.jorgemeza.medidaexacta.invoice.data.local.InvoiceDao
import com.jorgemeza.medidaexacta.invoice.data.local.entity.InvoiceSyncEntity
import com.jorgemeza.medidaexacta.invoice.data.mapper.toDomain
import com.jorgemeza.medidaexacta.invoice.data.mapper.toDto
import com.jorgemeza.medidaexacta.invoice.data.remote.IInvoiceApi
import com.jorgemeza.medidaexacta.quotation.data.local.QuotationDao
import com.jorgemeza.medidaexacta.quotation.data.local.entity.QuotationSyncEntity
import com.jorgemeza.medidaexacta.quotation.data.mapper.toDomain
import com.jorgemeza.medidaexacta.quotation.data.mapper.toDto
import com.jorgemeza.medidaexacta.quotation.data.remote.IQuotationApi
import com.jorgemeza.medidaexacta.shoppingCar.data.local.DetailDao
import com.jorgemeza.medidaexacta.shoppingCar.data.local.IDetailApi
import com.jorgemeza.medidaexacta.shoppingCar.data.local.entity.DetailSyncEntity
import com.jorgemeza.medidaexacta.shoppingCar.data.mapper.toDomain
import com.jorgemeza.medidaexacta.shoppingCar.data.mapper.toDto
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope

@HiltWorker
class ClientSyncWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val api: IClientApi,
    private val dao: ClientDao,
    private val quotationDao: QuotationDao,
    private val quotationApi: IQuotationApi,
    private val invoiceApi: IInvoiceApi,
    private val invoiceDao: InvoiceDao,
    private val detailDao: DetailDao,
    private val detailApi: IDetailApi
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        if (runAttemptCount >= 3) {
            return Result.failure()
        }

        val items = dao.getAllClientSync()
        val itemsQuotation = quotationDao.getAllQuotationSync()
        val itemsInvoice = invoiceDao.getAllInvoiceSync()
        val itemsDetail = detailDao.getAllDetailSync()

        return try {
            supervisorScope {
                val jobs = items.map { items -> async { sync(items) } }
                val jobsQuotations = itemsQuotation.map { items -> async { syncQuotations(items) } }
                val jobsInvoice = itemsInvoice.map { items -> async { syncInvoice(items) } }
                val jobsDetail = itemsDetail.map { items -> async { syncDetail(items) } }
                jobs.awaitAll()
                jobsQuotations.awaitAll()
                jobsInvoice.awaitAll()
                jobsDetail.awaitAll()
            }
            Log.i("ClientSyncWorker", "Synchronization completed successfully.")
            Result.success()
        } catch (e: Exception) {
            Log.i("ClientSyncWorker", "Error during synchronization: ${e.message}")
            Result.retry()
        }
    }

    private suspend fun sync(entity: ClientSyncEntity) {
        val client = dao.getClientById(entity.id).toDomain().toDto()
        resultOf {
            api.inserClient(client)
        }.onSuccess {
            dao.deleteClientSync(entity)
        }.onFailure {
            throw it
        }
    }

    private suspend fun syncQuotations(entity: QuotationSyncEntity) {
        val quotation = quotationDao.getQuotationByIdSync(entity.id).toDomain().toDto()
        resultOf {
            quotationApi.inserQuotation(quotation)
        }.onSuccess {
            quotationDao.deleteQuotationSync(entity)
        }.onFailure {
            throw it
        }
    }

    private suspend fun syncInvoice(entity: InvoiceSyncEntity) {
        val invoice = invoiceDao.getInvoiceById(entity.id).toDomain().toDto()
        resultOf {
            invoiceApi.inserInvocie(invoice)
        }.onSuccess {
            invoiceDao.deleteInvoiceSync(entity)
        }.onFailure {
            throw it
        }
    }

    private suspend fun syncDetail(entity: DetailSyncEntity) {
        val detail = detailDao.getDetailById(entity.id).toDomain().toDto()
        resultOf {
            detailApi.inserDetail(detail)
        }.onSuccess {
            detailDao.deleteDetailSync(entity)
        }.onFailure {
            throw it
        }
    }
}