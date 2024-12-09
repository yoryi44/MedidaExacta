package com.jorgemeza.medidaexacta.quotation.ui.list

import com.jorgemeza.medidaexacta.quotation.domain.model.QuotationModel

data class QuotationState(
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val quotations: List<QuotationModel> = emptyList(),
)
