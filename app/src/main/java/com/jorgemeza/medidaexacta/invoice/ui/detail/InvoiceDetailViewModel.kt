package com.jorgemeza.medidaexacta.invoice.ui.detail

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgemeza.medidaexacta.client.domain.usecase.GetAllClientUseCase
import com.jorgemeza.medidaexacta.invoice.domain.model.InvoiceModel
import com.jorgemeza.medidaexacta.invoice.domain.usecase.GenerateInvoicePdfUseCase
import com.jorgemeza.medidaexacta.invoice.domain.usecase.GetInvoiceByIdUseCase
import com.jorgemeza.medidaexacta.quotation.domain.model.QuotationModel
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetQuotationByIdUseCase
import com.jorgemeza.medidaexacta.shoppingCar.domain.usecase.GetDetailByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvoiceDetailViewModel  @Inject constructor(
    @ApplicationContext val context: Context,
    private val getQuotationByIdUseCase: GetQuotationByIdUseCase,
    private val getQuotationDetailByIdUseCase: GetDetailByIdUseCase,
    private val getAllClientsUseCase: GetAllClientUseCase,
    private val generateInvoicePdfUseCase: GenerateInvoicePdfUseCase,
    private val getInvoiceByIdUseCase: GetInvoiceByIdUseCase
) : ViewModel() {

    lateinit var quotation: QuotationModel
    lateinit var invoice: InvoiceModel

    private var pdfJob: Job? = null

    var state by mutableStateOf(InvoiceDetailState())
        private set

    init {
        loadInitialData()
    }

    fun onEvent(event: InvoiceDetailEvent) {
        when (event) {

            InvoiceDetailEvent.OnPdf -> createPDF()
            InvoiceDetailEvent.OnDismissDialog -> {
                state = state.copy(
                    error = null,
                )
            }

        }
    }

    fun getInvoiceById(invoiceId: String) {

        viewModelScope.launch {

            state = state.copy(
                isLoading = true
            )

            invoice = getInvoiceByIdUseCase(invoiceId)
            quotation = getQuotationByIdUseCase(invoice.quotation)
            val products = getQuotationDetailByIdUseCase(quotation.id)

            state = state.copy(
                id = quotation.id,
                client = quotation.client,
                quotationNumber = quotation.quotationNumber,
                products = products,
                price = products.sumOf { it.price.toDouble() }.toString(),
                date = quotation.date,
                isLoading = false
            )

        }

    }

    private fun loadInitialData() {
        viewModelScope.launch{
            getAllClientsUseCase().collect { clients ->
                state = state.copy(
                    clients = clients
                )
            }
        }
    }

    private fun createPDF() {

        if (!state.id.isNullOrEmpty()) {

            pdfJob?.cancel()
            pdfJob = viewModelScope.launch {
                state = state.copy(
                    isLoading = true
                )
                generateInvoicePdfUseCase(
                    client = state.clients.firstOrNull { it.name == state.client }!!,
                    quotation = quotation,
                    invoiceNumber = invoice.invoiceNumber,
                    state.products
                )
                state = state.copy(
                    isLoading = false
                )
            }
        }
    }

}