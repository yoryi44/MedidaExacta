package com.jorgemeza.medidaexacta.invoice.domain.usecase

import com.jorgemeza.medidaexacta.client.domain.model.ClientModel
import com.jorgemeza.medidaexacta.invoice.domain.pdf.IInvoicePdfGenerator
import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel
import com.jorgemeza.medidaexacta.quotation.domain.model.QuotationModel
import com.jorgemeza.medidaexacta.quotation.domain.pdf.IQuotationPdfGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GenerateInvoicePdfUseCase(
    private val pdfGenerator: IInvoicePdfGenerator
) {
    suspend operator fun invoke(
        client: ClientModel,
        quotation: QuotationModel,
        invoiceNumber: String,
        detail: List<DetailModel>
    ) = withContext(Dispatchers.Default) {
        pdfGenerator.pdfGenerator(quotation,invoiceNumber, client, detail)
    }
}