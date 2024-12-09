package com.jorgemeza.medidaexacta.quotation.ui.shoppingCar.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgemeza.medidaexacta.quotation.domain.model.QuotationModel
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetQuotationByIdUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetQuotationDetailByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ShoppingCarViewModel @Inject constructor(
    private val getQuotationDetailByIdUseCase: GetQuotationDetailByIdUseCase
) : ViewModel() {

    var state by mutableStateOf(ShoppingCarState())
        private set

    fun onEvent(event: ShoppingCarEvent) {
        when (event) {
            is ShoppingCarEvent.OnClientChange -> {
                //TODO
            }
        }
    }

    fun getQuotationById(quotationId: String) {

        viewModelScope.launch {

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