package com.jorgemeza.medidaexacta.shoppingCar.domain.usecase

import com.jorgemeza.medidaexacta.shoppingCar.domain.repository.IDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteDetailUseCase(
    private val detailRepository: IDetailRepository
) {
    suspend operator fun invoke(id: String) : Result<Unit> = withContext(Dispatchers.IO) {
        detailRepository.deleteDetail(id)
    }
}