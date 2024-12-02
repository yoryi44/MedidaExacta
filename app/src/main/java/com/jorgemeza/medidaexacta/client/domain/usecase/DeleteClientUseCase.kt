package com.jorgemeza.medidaexacta.client.domain.usecase

import com.jorgemeza.medidaexacta.client.domain.repository.IClientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteClientUseCase(
    private val clientRepository: IClientRepository
) {
    suspend operator fun invoke(clientId: String) : Result<Unit> = withContext(Dispatchers.IO) {
        clientRepository.deleteClient(clientId)
    }
}