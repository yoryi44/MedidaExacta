package com.jorgemeza.medidaexacta.quotation.domain.pdf

import com.jorgemeza.medidaexacta.client.domain.model.ClientModel
import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel
import com.jorgemeza.medidaexacta.quotation.domain.model.QuotationModel

interface IQuotationPdfGenerator {
    suspend fun pdfGenerator(quotation: QuotationModel, client: ClientModel, detail: List<DetailModel>)
}