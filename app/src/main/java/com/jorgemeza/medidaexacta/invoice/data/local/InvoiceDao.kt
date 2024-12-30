package com.jorgemeza.medidaexacta.invoice.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jorgemeza.medidaexacta.invoice.data.local.entity.InvoiceEntity
import com.jorgemeza.medidaexacta.invoice.data.local.entity.InvoiceSyncEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InvoiceDao {

    @Query("SELECT i.id,c.name as client,i.quotation,i.date,i.invoiceNumber " +
            "FROM InvoiceEntity i LEFT JOIN ClientEntity c ON i.client = c.id " +
            "LEFT JOIN QuotationEntity q ON q.id = i.quotation ORDER BY i.date,CAST(invoiceNumber AS INTEGER) ASC")
    fun getAllInvocie(): Flow<List<InvoiceEntity>>

    @Query("SELECT * FROM InvoiceEntity WHERE id = :id")
    suspend fun getInvoiceById(id: String): InvoiceEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInviceSync(invoiceSyncEntity: InvoiceSyncEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInvoice(invoiceEntity: InvoiceEntity)
//
//    @Query("DELETE FROM QuotationEntity WHERE id = :id")
//    fun deleteQuotationById(id: String)
//
//    @Query("DELETE FROM QuotationSyncEntity WHERE id = :id")
//    fun deleteQuotationSyncById(id: String)
//
//    @Query("DELETE FROM QuotationSyncEntity WHERE id = :id")
//    fun deleteQuotationSyncByClient(id: String)

    @Query("SELECT (COALESCE(MAX(CAST(invoiceNumber AS INTEGER)), 0) + 1) FROM InvoiceEntity")
    suspend fun getInvoiceConsecutive(): String

//    @Query("SELECT q.id,price,date,quotationNumber,c.name as client FROM QuotationEntity q LEFT JOIN ClientEntity c ON client = c.id WHERE client LIKE '%' || :search || '%' " +
//            "OR price LIKE '%' || :search || '%' OR date LIKE '%' || :search || '%' " +
//            "OR name LIKE '%' || :search || '%' " +
//            "ORDER BY date ASC")
//    fun getQuotationBySearch(search: String): List<QuotationEntity>

    @Delete
    suspend fun deleteInvoiceSync(invoiceSyncEntity: InvoiceSyncEntity)

    @Query("SELECT * FROM InvoiceSyncEntity")
    fun getAllInvoiceSync(): List<InvoiceSyncEntity>

}