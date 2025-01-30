package com.jorgemeza.medidaexacta.quotation.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuotationEntity (
    @PrimaryKey(autoGenerate = false)
    val id : String,
    val client : String,
    val quotationNumber : String,
    val price : String,
    val date : String,
    val observation : String?
)