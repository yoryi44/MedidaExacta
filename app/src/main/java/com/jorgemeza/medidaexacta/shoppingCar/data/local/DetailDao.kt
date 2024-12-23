package com.jorgemeza.medidaexacta.shoppingCar.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jorgemeza.medidaexacta.shoppingCar.data.local.entity.DetailSyncEntity
import com.jorgemeza.medidaexacta.shoppingCar.data.local.entity.QuotationDetailEntity

@Dao
interface DetailDao {

    @Query("SELECT * FROM QuotationDetailEntity WHERE quotation = :quotation")
    suspend fun getDetailByQuotationId(quotation: String): List<QuotationDetailEntity>

    @Query("SELECT * FROM QuotationDetailEntity WHERE id = :id")
    suspend fun getDetailById(id: String): QuotationDetailEntity

    @Query("SELECT * FROM QuotationDetailEntity WHERE id = :detail")
    suspend fun getDetailProductById(detail: String): QuotationDetailEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailSync(detailSyncEntity: DetailSyncEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetail(quotationDetail: QuotationDetailEntity)

    @Query("DELETE FROM QuotationDetailEntity WHERE id = :id")
    fun deleteDetailById(id: String)

    @Query("DELETE FROM QuotationEntity WHERE id = :id")
    fun deleteQuotationById(id: String)

    @Query("DELETE FROM DetailSyncEntity WHERE id = :id")
    fun deleteQuotationDetailSyncById(id: String)

    @Query("SELECT * FROM DetailSyncEntity")
    fun getAllDetailSync(): List<DetailSyncEntity>

    @Delete
    suspend fun deleteDetailSync(detailSyncEntity: DetailSyncEntity)

}