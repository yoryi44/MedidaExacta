package com.jorgemeza.medidaexacta.shoppingCar.ui.detail

data class ShoppingCarDetailState(
    val id : String? = null,
    val quotation : String = "",
    val amount : String = "",
    val price : String = "",
    val product : String = "",
    val isSaveSuccessful : Boolean = false,
    val isLoading: Boolean = false
)
