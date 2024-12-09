package com.jorgemeza.medidaexacta.quotation.domain.usecase

import com.jorgemeza.medidaexacta.quotation.domain.model.QuotationModel
import com.jorgemeza.medidaexacta.quotation.domain.repository.IQuotationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddQuotationUseCase(
    private val quotationRepository: IQuotationRepository
) {
    suspend operator fun invoke(quotation: QuotationModel) = withContext(Dispatchers.IO) {
        quotationRepository.addQuotationUseCase(quotation)
    }
}