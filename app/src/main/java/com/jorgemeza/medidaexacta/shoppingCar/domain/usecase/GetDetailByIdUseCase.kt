package com.jorgemeza.medidaexacta.shoppingCar.domain.usecase

import com.jorgemeza.medidaexacta.shoppingCar.domain.repository.IDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetDetailByIdUseCase(
    private val detailRepository: IDetailRepository
) {
    suspend operator fun invoke(quotationId: String) = withContext(Dispatchers.IO) {
        detailRepository.getDetailById(quotationId)
    }
}
