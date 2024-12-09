package com.jorgemeza.medidaexacta.quotation.ui.shoppingCar.list

import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel

data class ShoppingCarState(
    val id : String? = null,
    val products : List<DetailModel> = emptyList(),
    val isSaveSuccessful : Boolean = false,
    val isLoading: Boolean = false
)
