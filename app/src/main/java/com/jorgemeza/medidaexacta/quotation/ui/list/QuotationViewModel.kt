package com.jorgemeza.medidaexacta.quotation.ui.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetAllQuotationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotationViewModel @Inject constructor(
    private val getAllQuotationUseCase: GetAllQuotationUseCase
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

            is QuotationEvent.OnDeleteClient -> TODO()
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