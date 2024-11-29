package com.jorgemeza.medidaexacta.client.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ClientSyncEntity (
    @PrimaryKey(autoGenerate = false)
    val id: String
)