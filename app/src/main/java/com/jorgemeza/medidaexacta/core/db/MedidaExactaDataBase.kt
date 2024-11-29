package com.jorgemeza.medidaexacta.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jorgemeza.medidaexacta.client.data.local.ClientDao
import com.jorgemeza.medidaexacta.client.data.local.entity.ClientEntity
import com.jorgemeza.medidaexacta.client.data.local.entity.ClientSyncEntity

@Database(entities = [ClientEntity::class, ClientSyncEntity::class], version = 1)
abstract class MedidaExactaDataBase : RoomDatabase() {
    abstract val clientDao: ClientDao
}