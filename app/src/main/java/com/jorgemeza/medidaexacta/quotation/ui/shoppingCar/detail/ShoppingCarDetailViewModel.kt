package com.jorgemeza.medidaexacta.quotation.ui.shoppingCar.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgemeza.medidaexacta.quotation.domain.model.QuotationModel
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetQuotationByIdUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetQuotationDetailProductByIdUseCase
import com.jorgemeza.medidaexacta.quotation.ui.detail.QuotationDetailEvent
import com.jorgemeza.medidaexacta.quotation.ui.detail.QuotationDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ShoppingCarDetailViewModel @Inject constructor(
//    private val addQuotationUserCase: AddQuotationUseCase,
    private val getQuotationDetailProductByIdUseCase: GetQuotationDetailProductByIdUseCase
) : ViewModel() {

    var state by mutableStateOf(ShoppingCarDetailState())
        private set

    fun onEvent(event: QuotationDetailEvent) {
        when (event) {
            is QuotationDetailEvent.OnClientChange -> {
//                state = state.copy(client = event.client)
            }
            QuotationDetailEvent.OnSave -> {
//                viewModelScope.launch {
//
//                    QuotationModel(
//                        id = state.id ?: UUID.randomUUID().toString(),
//                        client = state.client,
//                        quotationNumber = state.quotationNumber,
//                        price = state.price,
//                        date = state.date,
//                    )
//
////                    addQuotationUserCase(Quotation)
//                }
//                state = state.copy(
//                    isSaveSuccessful = true
//                )
            }
        }
    }

    fun getProductById(productId: String) {

        viewModelScope.launch {

            state = state.copy(
                isLoading = true
            )

            val product = getQuotationDetailProductByIdUseCase(productId)

            state = state.copy(
                id = product.id,
                amount = product.amount,
                price = product.price,
                product = product.product,
                isLoading = false
            )
        }

    }

}