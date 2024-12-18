package com.jorgemeza.medidaexacta.invoice.domain.usecase

import com.jorgemeza.medidaexacta.invoice.domain.model.InvoiceModel
import com.jorgemeza.medidaexacta.invoice.domain.repository.IInvoiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddInvoiceUseCase(
    private val invoiceRepository: IInvoiceRepository
) {
    suspend operator fun invoke(invoice: InvoiceModel) = withContext(Dispatchers.IO) {
        invoiceRepository.addInvoice(invoice)
    }
}