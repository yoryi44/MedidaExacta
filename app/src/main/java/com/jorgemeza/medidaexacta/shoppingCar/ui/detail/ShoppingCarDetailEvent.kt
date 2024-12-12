package com.jorgemeza.medidaexacta.shoppingCar.ui.detail

sealed interface ShoppingCarDetailEvent {
    data class OnProductEvent(val product: String) : ShoppingCarDetailEvent
    data class OnPriceEvent(val price: String) : ShoppingCarDetailEvent
    data class OnAmountEvent(val amount: String) : ShoppingCarDetailEvent
    data class OnQuotationIdEvent(val quotationId: String) : ShoppingCarDetailEvent
    object OnSave : ShoppingCarDetailEvent
}