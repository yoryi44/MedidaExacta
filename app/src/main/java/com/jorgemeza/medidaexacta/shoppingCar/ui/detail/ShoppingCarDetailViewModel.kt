package com.jorgemeza.medidaexacta.shoppingCar.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel
import com.jorgemeza.medidaexacta.shoppingCar.domain.usecase.GetDetailProductByIdUseCase
import com.jorgemeza.medidaexacta.shoppingCar.domain.usecase.AddDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ShoppingCarDetailViewModel @Inject constructor(
    private val addDetailUseCasea: AddDetailUseCase,
    private val getQuotationDetailProductByIdUseCase: GetDetailProductByIdUseCase
) : ViewModel() {

    var state by mutableStateOf(ShoppingCarDetailState())
        private set

    fun onEvent(event: ShoppingCarDetailEvent) {
        when (event) {
            is ShoppingCarDetailEvent.OnAmountEvent -> {
                state = state.copy(
                    amount = event.amount
                )
            }

            is ShoppingCarDetailEvent.OnPriceEvent -> {
                state = state.copy(
                    price = event.price
                )
            }

            is ShoppingCarDetailEvent.OnProductEvent -> {
                state = state.copy(
                    product = event.product
                )
            }

            ShoppingCarDetailEvent.OnSave -> {
                viewModelScope.launch {
                    val detail = DetailModel(
                        id = state.id ?: UUID.randomUUID().toString(),
                        amount = state.amount,
                        price = state.price,
                        quotation = state.quotation,
                        product = state.product,
                    )
                    addDetailUseCasea(detail)
                }

                state = state.copy(
                    isSaveSuccessful = true
                )
            }

            is ShoppingCarDetailEvent.OnQuotationIdEvent -> state =
                state.copy(quotation = event.quotationId)
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
                quotation = product.quotation,
                price = product.price,
                product = product.product,
                isLoading = false
            )
        }

    }

}