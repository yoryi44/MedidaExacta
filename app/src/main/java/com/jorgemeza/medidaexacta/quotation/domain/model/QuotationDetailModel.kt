package com.jorgemeza.medidaexacta.quotation.domain.model

data class QuotationDetailModel (
    val id : String,
    val client : String,
    val products : List<DetailModel>,
    val quotationNumber : String,
    val price : String,
    val date : String,
)