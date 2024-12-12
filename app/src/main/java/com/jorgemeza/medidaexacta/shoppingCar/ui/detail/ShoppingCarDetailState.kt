package com.jorgemeza.medidaexacta.shoppingCar.ui.detail

import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel

data class ShoppingCarDetailState(
    val id : String? = null,
    val quotation : String = "",
    val amount : String = "",
    val price : String = "",
    val product : String = "",
    val isSaveSuccessful : Boolean = false,
    val isLoading: Boolean = false
)
