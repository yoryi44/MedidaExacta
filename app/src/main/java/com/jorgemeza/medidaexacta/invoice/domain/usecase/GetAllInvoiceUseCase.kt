package com.jorgemeza.medidaexacta.invoice.domain.usecase

import com.jorgemeza.medidaexacta.invoice.domain.repository.IInvoiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext

class GetAllInvoiceUseCase(
    private val invoiceRepository: IInvoiceRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        invoiceRepository.getAllInvoice().distinctUntilChanged()
    }
}