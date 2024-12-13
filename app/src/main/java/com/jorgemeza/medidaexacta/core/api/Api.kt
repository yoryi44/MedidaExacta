package com.jorgemeza.medidaexacta.core.api

object Api {

    const val BASE_URL = "https://my-appstore-apps-default-rtdb.firebaseio.com/MedidaExacta/"

    //CLIENT
    const val CLIENT_URL = "clients.json"
    const val CLIENT_DELETE_URL = "clients/{clientId}.json"

    //QUOTATION
    const val QUOTATION_URL = "quotations.json"
    const val QUOTATION_DELETE_URL = "quotations/{quotationId}.json"

    //DETAIL
    const val DETAIL_URL = "details.json"
    const val DETAIL_DELETE_URL = "details/{detailId}.json"

    //INVOICE
    const val INVOICE_URL = "invoices.json"
    const val INVOICE_DELETE_URL = "invoices/{invoicesId}.json"

}