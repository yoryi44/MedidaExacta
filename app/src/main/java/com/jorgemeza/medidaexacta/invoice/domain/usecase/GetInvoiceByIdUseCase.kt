package com.jorgemeza.medidaexacta.invoice.domain.usecase

import com.jorgemeza.medidaexacta.invoice.domain.repository.IInvoiceRepository
import com.jorgemeza.medidaexacta.quotation.domain.repository.IQuotationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetInvoiceByIdUseCase(
    private val invoiceRepository: IInvoiceRepository
) {
    suspend operator fun invoke(invoiceId: String) = withContext(Dispatchers.IO) {
        invoiceRepository.getInvoiceById(invoiceId)
    }
}