package com.jorgemeza.medidaexacta.quotation.ui.shoppingCar.list

sealed interface ShoppingCarEvent {
    data class OnClientChange(val client: String) : ShoppingCarEvent
}