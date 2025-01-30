package com.jorgemeza.medidaexacta.invoice.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InvoiceEntity (
    @PrimaryKey(autoGenerate = false)
    val id : String,
    val invoiceNumber : String,
    val client : String,
    val quotation : String,
    val date : String,
    val observations : String?
)