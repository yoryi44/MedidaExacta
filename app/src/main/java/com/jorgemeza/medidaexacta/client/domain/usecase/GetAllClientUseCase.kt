package com.jorgemeza.medidaexacta.client.domain.usecase

import com.jorgemeza.medidaexacta.client.domain.repository.IClientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext

class GetAllClientUseCase(
    private val clientRepository: IClientRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        clientRepository.getAllClient().distinctUntilChanged()
    }
}