package com.jorgemeza.medidaexacta.client.domain.usecase

import com.jorgemeza.medidaexacta.client.domain.repository.IClientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SyncClientUseCase (
    private val clientRepository: IClientRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        clientRepository.syncClient()
    }
}