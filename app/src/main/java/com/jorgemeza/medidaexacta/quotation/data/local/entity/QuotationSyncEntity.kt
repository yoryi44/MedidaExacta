package com.jorgemeza.medidaexacta.quotation.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuotationSyncEntity (
    @PrimaryKey(autoGenerate = false)
    val id : String
)