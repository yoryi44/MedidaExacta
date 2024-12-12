package com.jorgemeza.medidaexacta.quotation.domain.usecase

import com.jorgemeza.medidaexacta.quotation.domain.repository.IQuotationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteQuotationUseCase(
    private val quotationRepository: IQuotationRepository
) {
    suspend operator fun invoke(id: String) : Result<Unit> = withContext(Dispatchers.IO) {
        quotationRepository.deleteQuotationUseCase(id)
    }
}