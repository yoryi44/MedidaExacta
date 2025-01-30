package com.jorgemeza.medidaexacta.quotation.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jorgemeza.medidaexacta.client.data.local.entity.ClientSyncEntity
import com.jorgemeza.medidaexacta.quotation.data.local.entity.QuotationEntity
import com.jorgemeza.medidaexacta.quotation.data.local.entity.QuotationSyncEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuotationDao {

    @Query("SELECT q.observation,q.id,price,date,quotationNumber,c.name as client FROM QuotationEntity q LEFT JOIN ClientEntity c ON client = c.id ORDER BY date,name DESC")
    fun getAllQuotation(): Flow<List<QuotationEntity>>

    @Query("SELECT q.observation,q.id,price,date,quotationNumber,c.name as client FROM QuotationEntity q LEFT JOIN ClientEntity c ON client = c.id ORDER BY date,name DESC")
    fun getAllQuotationMain(): List<QuotationEntity>

    @Query("SELECT q.observation,q.id,price,date,quotationNumber,c.name as client FROM QuotationEntity q LEFT JOIN ClientEntity c ON client = c.id WHERE q.id = :id")
    suspend fun getQuotationById(id: String): QuotationEntity

    @Query("SELECT * FROM QuotationEntity WHERE id = :id")
    suspend fun getQuotationByIdSync(id: String): QuotationEntity

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

    @Query("SELECT q.observation,q.id,price,date,quotationNumber,c.name as client FROM QuotationEntity q LEFT JOIN ClientEntity c ON client = c.id WHERE client LIKE '%' || :search || '%' " +
            "OR price LIKE '%' || :search || '%' OR date LIKE '%' || :search || '%' " +
            "OR name LIKE '%' || :search || '%' " +
            "ORDER BY date ASC")
    fun getQuotationBySearch(search: String): List<QuotationEntity>

    @Delete
    suspend fun deleteQuotationSync(quotationSyncEntity: QuotationSyncEntity)

    @Query("SELECT * FROM QuotationSyncEntity")
    fun getAllQuotationSync(): List<QuotationSyncEntity>

}