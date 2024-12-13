package com.jorgemeza.medidaexacta.quotation.domain.usecase

import com.jorgemeza.medidaexacta.quotation.domain.repository.IQuotationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext

class GetAllQuotationUseCase(
    private val quotationRepository: IQuotationRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        quotationRepository.getAllQuotation().distinctUntilChanged()
    }
}