package com.jorgemeza.medidaexacta.quotation.domain.model

data class QuotationModel (
    val id : String,
    val client : String,
    val quotationNumber : String,
    val price : String,
    val date : String,
    val observation : String?
)