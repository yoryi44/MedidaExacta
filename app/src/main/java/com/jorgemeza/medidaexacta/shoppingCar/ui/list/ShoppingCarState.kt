package com.jorgemeza.medidaexacta.shoppingCar.ui.list

import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel

data class ShoppingCarState(
    val id : String? = null,
    val products : List<DetailModel> = emptyList(),
    val isSaveSuccessful : Boolean = false,
    val idDetailDelete: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
