package com.jorgemeza.medidaexacta.client.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jorgemeza.medidaexacta.client.data.local.entity.ClientEntity
import com.jorgemeza.medidaexacta.client.data.local.entity.ClientSyncEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClient(clientEntity: ClientEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertClientSync(clientsyncEntity: ClientSyncEntity)

    @Query("SELECT * FROM ClientEntity ORDER BY name ASC")
    fun getAllClient(): Flow<List<ClientEntity>>

    @Query("SELECT * FROM ClientEntity WHERE name LIKE '%' || :search || '%' " +
            "OR phone LIKE '%' || :search || '%' OR email LIKE '%' || :search || '%' " +
            "ORDER BY name ASC")
    fun getClientBySeacrh(search: String): List<ClientEntity>

    @Query("SELECT * FROM ClientEntity WHERE id = :id")
    suspend fun getClientById(id: String): ClientEntity

    @Query("DELETE FROM ClientEntity WHERE id = :clientId")
    fun deleteClientById(clientId: String)

    @Query("DELETE FROM ClientSyncEntity WHERE id = :clientId")
    fun deleteClientSyncById(clientId: String)

    @Query("SELECT * FROM ClientSyncEntity")
    fun getAllClientSync(): List<ClientSyncEntity>

    @Delete
    suspend fun deleteClientSync(clientSyncEntity: ClientSyncEntity)

}