package com.jorgemeza.medidaexacta.quotation.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgemeza.medidaexacta.quotation.domain.model.QuotationModel
import com.jorgemeza.medidaexacta.quotation.domain.usecase.AddQuotationUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetQuotationByIdUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetQuotationDetailByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class QuotationDetailViewModel @Inject constructor(
    private val addQuotationUserCase: AddQuotationUseCase,
    private val getQuotationByIdUseCase: GetQuotationByIdUseCase,
    private val getQuotationDetailByIdUseCase: GetQuotationDetailByIdUseCase
) : ViewModel() {

    var state by mutableStateOf(QuotationDetailState())
        private set

    fun onEvent(event: QuotationDetailEvent) {
        when (event) {

            is QuotationDetailEvent.OnClientChange -> {
                state = state.copy(client = event.client)
            }

            QuotationDetailEvent.OnSave -> {
                viewModelScope.launch {

                    val quotation = QuotationModel(
                        id = state.id ?: UUID.randomUUID().toString(),
                        client = state.client,
                        quotationNumber = state.quotationNumber,
                        price = state.price,
                        date = state.date,
                    )

                    addQuotationUserCase(quotation)
                }
                state = state.copy(
                    isSaveSuccessful = true
                )
            }
        }
    }

    fun getQuotationById(quotationId: String) {

        viewModelScope.launch {

            state = state.copy(
                isLoading = true
            )

            val quotation = getQuotationByIdUseCase(quotationId)
            val products = getQuotationDetailByIdUseCase(quotationId)

            state = state.copy(
                id = quotation.id,
                client = quotation.client,
                quotationNumber = quotation.quotationNumber,
                products = products,
                price = quotation.price,
                date = quotation.date,
                isLoading = false
            )
        }

    }

}