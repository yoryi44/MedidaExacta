package com.jorgemeza.medidaexacta.shoppingCar.domain.usecase

import com.jorgemeza.medidaexacta.shoppingCar.domain.repository.IDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAllQuotationDetailUseCase(
    private val detailRepository: IDetailRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        detailRepository.getAllDetail()
    }
}