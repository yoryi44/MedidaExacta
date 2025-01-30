package com.jorgemeza.medidaexacta.invoice.domain.model

data class InvoiceModel (
    val id : String,
    val invoiceNumber : String,
    val quotation : String,
    val client : String,
    val date : String,
    val observations : String?
)