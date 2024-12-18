package com.jorgemeza.medidaexacta.quotation.domain.usecase

import com.jorgemeza.medidaexacta.client.domain.model.ClientModel
import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel
import com.jorgemeza.medidaexacta.quotation.domain.model.QuotationModel
import com.jorgemeza.medidaexacta.quotation.domain.pdf.IQuotationPdfGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GenerateQuotationPdfUseCase(
    private val pdfGenerator: IQuotationPdfGenerator
) {
    suspend operator fun invoke(
        client: ClientModel,
        quotation: QuotationModel,
        detail: List<DetailModel>
    ) = withContext(Dispatchers.Default) {
        pdfGenerator.pdfGenerator(quotation, client, detail)
    }
}