package com.jorgemeza.medidaexacta.quotation.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jorgemeza.medidaexacta.quotation.data.local.entity.QuotationEntity
import com.jorgemeza.medidaexacta.quotation.data.local.entity.QuotationSyncEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuotationDao {

    @Query("SELECT q.id,price,date,quotationNumber,c.name as client FROM QuotationEntity q LEFT JOIN ClientEntity c ON client = c.id ORDER BY date DESC")
    fun getAllQuotation(): Flow<List<QuotationEntity>>

    @Query("SELECT q.id,price,date,quotationNumber,c.name as client FROM QuotationEntity q LEFT JOIN ClientEntity c ON client = c.id WHERE q.id = :id")
    suspend fun getQuotationById(id: String): QuotationEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuotationSync(quotationSyncEntity: QuotationSyncEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuotation(quotation: QuotationEntity)

    @Query("DELETE FROM QuotationEntity WHERE id = :id")
    fun deleteQuotationById(id: String)

    @Query("DELETE FROM QuotationSyncEntity WHERE id = :id")
    fun deleteQuotationSyncById(id: String)

    @Query("DELETE FROM QuotationSyncEntity WHERE id = :id")
    fun deleteQuotationSyncByClient(id: String)

    @Query("SELECT (MAX(quotationNumber) +1) FROM QuotationEntity")
    suspend fun getQuotationConsecutive(): String

    @Query("SELECT q.id,price,date,quotationNumber,c.name as client FROM QuotationEntity q LEFT JOIN ClientEntity c ON client = c.id WHERE client LIKE '%' || :search || '%' " +
            "OR price LIKE '%' || :search || '%' OR date LIKE '%' || :search || '%' " +
            "OR name LIKE '%' || :search || '%' " +
            "ORDER BY date ASC")
    fun getQuotationBySearch(search: String): List<QuotationEntity>

}