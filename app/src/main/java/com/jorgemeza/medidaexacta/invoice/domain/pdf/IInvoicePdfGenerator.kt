package com.jorgemeza.medidaexacta.invoice.domain.pdf

import com.jorgemeza.medidaexacta.client.domain.model.ClientModel
import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel
import com.jorgemeza.medidaexacta.quotation.domain.model.QuotationModel

interface IInvoicePdfGenerator {
    suspend fun pdfGenerator(quotation: QuotationModel, invoiceNumber: String, client: ClientModel, detail: List<DetailModel>)
}