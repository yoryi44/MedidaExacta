package com.jorgemeza.medidaexacta.quotation.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuotationDetailEntity (
    @PrimaryKey(autoGenerate = false)
    val id : String,
    val quotation: String,
    val amount : String,
    val price : String,
    val product : String,
)