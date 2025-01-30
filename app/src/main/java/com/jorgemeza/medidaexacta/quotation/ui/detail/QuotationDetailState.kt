package com.jorgemeza.medidaexacta.quotation.ui.detail

import com.jorgemeza.medidaexacta.client.domain.model.ClientModel
import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel

data class QuotationDetailState(
    val id : String? = null,
    val client : String = "",
    val products : List<DetailModel> = emptyList(),
    val clients : List<ClientModel> = emptyList(),
    val quotationNumber : String = "",
    val observations : String? = "",
    val price : String = "",
    val date : String = "",
    val isSaveSuccessful : Boolean = false,
    val error : String? = null,
    val invoice : Boolean = false,
    val isLoading: Boolean = false
)
