package com.jorgemeza.medidaexacta.shoppingCar.ui.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgemeza.medidaexacta.shoppingCar.domain.usecase.DeleteDetailUseCase
import com.jorgemeza.medidaexacta.shoppingCar.domain.usecase.GetDetailByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingCarViewModel @Inject constructor(
    private val getQuotationDetailByIdUseCase: GetDetailByIdUseCase,
    private val deleteQuotationDetailUseCase: DeleteDetailUseCase
) : ViewModel() {

    private var currentDayJob : Job? = null

    var state by mutableStateOf(ShoppingCarState())
        private set

    fun onEvent(event: ShoppingCarEvent) {
        when (event) {
            is ShoppingCarEvent.OnDelete -> {
                state = state.copy(idDetailDelete = event.id)
            }
            ShoppingCarEvent.OnConfirmDialog -> {
                deleteDetail()
            }
            ShoppingCarEvent.OnDismissDialog -> {
                state = state.copy(
                    idDetailDelete = null,
                    error = null
                )
            }
        }
    }

    private fun deleteDetail() {
        viewModelScope.launch {
            deleteQuotationDetailUseCase(state.idDetailDelete!!).onFailure {
                state = state.copy(
                    error = it.message
                )
            }.onSuccess {
                getQuotationById(state.id!!)
            }
            state = state.copy(idDetailDelete = null)
        }
    }

    fun getQuotationById(quotationId: String) {

        currentDayJob?.cancel()
        currentDayJob = viewModelScope.launch {

            state = state.copy(
                isLoading = true
            )

            val detail = getQuotationDetailByIdUseCase(quotationId)

            state = state.copy(
                id = quotationId,
                products = detail,
                isLoading = false
            )
        }

    }

}