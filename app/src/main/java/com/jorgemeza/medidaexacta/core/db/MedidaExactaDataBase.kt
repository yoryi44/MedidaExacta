package com.jorgemeza.medidaexacta.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jorgemeza.medidaexacta.client.data.local.ClientDao
import com.jorgemeza.medidaexacta.client.data.local.entity.ClientEntity
import com.jorgemeza.medidaexacta.client.data.local.entity.ClientSyncEntity
import com.jorgemeza.medidaexacta.quotation.data.local.QuotationDao
import com.jorgemeza.medidaexacta.shoppingCar.data.local.entity.DetailSyncEntity
import com.jorgemeza.medidaexacta.shoppingCar.data.local.entity.QuotationDetailEntity
import com.jorgemeza.medidaexacta.quotation.data.local.entity.QuotationEntity
import com.jorgemeza.medidaexacta.quotation.data.local.entity.QuotationSyncEntity
import com.jorgemeza.medidaexacta.shoppingCar.data.local.DetailDao

@Database(
    entities = [ClientEntity::class, ClientSyncEntity::class, QuotationEntity::class,
        QuotationSyncEntity::class, QuotationDetailEntity::class, DetailSyncEntity::class],
    version = 1
)
abstract class MedidaExactaDataBase : RoomDatabase() {
    abstract val clientDao: ClientDao
    abstract val quotationDao: QuotationDao
    abstract val detailDao: DetailDao
}