package com.jorgemeza.medidaexacta.shoppingCar.domain.usecase

import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel
import com.jorgemeza.medidaexacta.shoppingCar.domain.repository.IDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddDetailUseCase(
    private val detailRepository: IDetailRepository
) {
    suspend operator fun invoke(detail: DetailModel) = withContext(Dispatchers.IO) {
        detailRepository.addDetailUseCase(detail)
    }
}