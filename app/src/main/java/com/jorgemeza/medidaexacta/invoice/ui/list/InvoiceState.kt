package com.jorgemeza.medidaexacta.invoice.ui.list

import com.jorgemeza.medidaexacta.invoice.domain.model.InvoiceModel

data class InvoiceState(
    val searchQuery: String = "",
    val invoices: List<InvoiceModel> = emptyList<InvoiceModel>(),
    val isLoading: Boolean = false
)
