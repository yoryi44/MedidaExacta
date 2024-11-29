package com.jorgemeza.medidaexacta.client.domain.usecase

import com.jorgemeza.medidaexacta.client.domain.repository.IClientRepository
import com.jorgemeza.medidaexacta.client.ui.list.models.ClientModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddClientUseCase(
    private val clientRepository: IClientRepository
) {
    suspend operator fun invoke(client: ClientModel) = withContext(Dispatchers.IO) {
        clientRepository.addClient(client)
    }
}