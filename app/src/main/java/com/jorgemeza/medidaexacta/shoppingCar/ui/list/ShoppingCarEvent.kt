package com.jorgemeza.medidaexacta.shoppingCar.ui.list

sealed interface ShoppingCarEvent {
    data class OnDelete(val id: String) : ShoppingCarEvent
    object OnDismissDialog : ShoppingCarEvent
    object OnConfirmDialog : ShoppingCarEvent
}