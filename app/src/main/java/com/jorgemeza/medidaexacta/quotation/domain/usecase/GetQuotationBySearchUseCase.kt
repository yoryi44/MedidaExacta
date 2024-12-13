package com.jorgemeza.medidaexacta.quotation.domain.usecase

import com.jorgemeza.medidaexacta.quotation.domain.repository.IQuotationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetQuotationBySearchUseCase (
    private val quotationRepository: IQuotationRepository
) {
    suspend operator fun invoke(id: String) = withContext(Dispatchers.IO) {
        quotationRepository.getQuotationBySearch(id)
    }
}