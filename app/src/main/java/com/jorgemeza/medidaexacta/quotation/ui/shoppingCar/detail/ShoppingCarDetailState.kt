package com.jorgemeza.medidaexacta.quotation.ui.shoppingCar.detail

import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel

data class ShoppingCarDetailState(
    val id : String = "",
    val amount : String = "",
    val price : String = "",
    val product : String = "",
    val isSaveSuccessful : Boolean = false,
    val isLoading: Boolean = false
)
