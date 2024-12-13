package com.jorgemeza.medidaexacta.invoice.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InvoiceSyncEntity (
    @PrimaryKey(autoGenerate = false)
    val id : String
)