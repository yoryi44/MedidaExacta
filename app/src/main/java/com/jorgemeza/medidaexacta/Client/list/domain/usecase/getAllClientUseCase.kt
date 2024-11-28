package com.jorgemeza.medidaexacta.Client.list.domain.usecase

import com.jorgemeza.medidaexacta.Client.list.domain.repository.IClientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class getAllClientUseCase(
    private val clientRepository: IClientRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        clientRepository.getAllClient()
    }
}