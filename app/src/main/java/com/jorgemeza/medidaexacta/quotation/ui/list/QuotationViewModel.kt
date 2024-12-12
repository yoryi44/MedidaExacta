package com.jorgemeza.medidaexacta.quotation.ui.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgemeza.medidaexacta.quotation.domain.usecase.DeleteQuotationUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetAllQuotationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotationViewModel @Inject constructor(
    private val getAllQuotationUseCase: GetAllQuotationUseCase,
    private val deteleteQuotationUseCase: DeleteQuotationUseCase
) : ViewModel() {

    var state by mutableStateOf(QuotationState())
        private set

    init {
        getAllQuotation()
    }

    fun onEvent(event: QuotationEvent) {
        when (event) {
            is QuotationEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
            }

            is QuotationEvent.OnDeleteQuotation -> {
                state = state.copy(idQuotationDelete = event.id)
            }
            QuotationEvent.OnConfirmDialog -> {
                deleteQuotation(state.idQuotationDelete!!)
            }
            QuotationEvent.OnDismissDialog -> {
                state = state.copy(
                    idQuotationDelete = null,
                    error = null
                )
            }
        }
    }

    private fun deleteQuotation(quotationId: String) {
        viewModelScope.launch {
            deteleteQuotationUseCase(quotationId).onFailure {
                state = state.copy(
                    error = it.message,
                )
            }
            state = state.copy(
                idQuotationDelete = null
            )
        }
    }

    fun getAllQuotation() {

        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )

            getAllQuotationUseCase().collectLatest { quotations ->
                state = state.copy(
                    quotations = quotations,
                    isLoading = false
                )
            }
        }
    }
}