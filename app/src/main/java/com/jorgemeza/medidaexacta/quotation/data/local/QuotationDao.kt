package com.jorgemeza.medidaexacta.quotation.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jorgemeza.medidaexacta.quotation.data.local.entity.QuotationDetailEntity
import com.jorgemeza.medidaexacta.quotation.data.local.entity.QuotationEntity
import com.jorgemeza.medidaexacta.quotation.data.local.entity.QuotationSyncEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuotationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuotation(quotation: QuotationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuotationDetail(quotationDetail: QuotationDetailEntity)

    @Query("SELECT * FROM QuotationEntity ORDER BY date DESC")
    fun getAllQuotation(): Flow<List<QuotationEntity>>

    @Query("SELECT * FROM QuotationEntity WHERE id = :id")
    suspend fun getQuotationById(id: String): QuotationEntity

    @Query("SELECT * FROM QuotationDetailEntity WHERE quotation = :quotation")
    suspend fun getQuotationDetailById(quotation: String): List<QuotationDetailEntity>

    @Query("SELECT * FROM QuotationDetailEntity WHERE id = :detail")
    suspend fun getQuotationDetailProductById(detail: String): QuotationDetailEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuotationSync(quotationSyncEntity: QuotationSyncEntity)

}