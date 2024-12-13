package com.jorgemeza.medidaexacta.quotation.ui.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgemeza.medidaexacta.quotation.domain.usecase.DeleteQuotationUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetAllQuotationDetailUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetAllQuotationUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetQuotationBySearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotationViewModel @Inject constructor(
    private val getAllQuotationUseCase: GetAllQuotationUseCase,
    private val deleteQuotationUseCase: DeleteQuotationUseCase,
    private val getAllQuotationDetailUseCase: GetAllQuotationDetailUseCase,
    private val getQuotationBySearchUseCase: GetQuotationBySearchUseCase
) : ViewModel() {

    private var quotationJob : Job? = null

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
            QuotationEvent.OnSearchQuotation -> {
                viewModelScope.launch {
                    state = state.copy(
                        quotations = getQuotationBySearchUseCase(state.searchQuery)
                    )
                }
            }
        }
    }

    private fun deleteQuotation(quotationId: String) {
        viewModelScope.launch {
            deleteQuotationUseCase(quotationId).onFailure {
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

        quotationJob?.cancel()
        quotationJob = viewModelScope.launch {

            state = state.copy(
                isLoading = true
            )

            getAllQuotationDetailUseCase()

            getAllQuotationUseCase().collectLatest { quotations ->
                state = state.copy(
                    quotations = quotations,
                    isLoading = false
                )
            }
        }
    }
}