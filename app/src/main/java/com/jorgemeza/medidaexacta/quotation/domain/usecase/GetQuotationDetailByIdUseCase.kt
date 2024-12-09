package com.jorgemeza.medidaexacta.quotation.domain.usecase

import com.jorgemeza.medidaexacta.quotation.domain.repository.IQuotationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetQuotationDetailByIdUseCase(
    private val quotationRepository: IQuotationRepository
) {
    suspend operator fun invoke(quotationId: String) = withContext(Dispatchers.IO) {
        quotationRepository.getQuotationDetailById(quotationId)
    }
}
