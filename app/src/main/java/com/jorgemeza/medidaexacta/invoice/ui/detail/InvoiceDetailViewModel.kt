package com.jorgemeza.medidaexacta.invoice.ui.detail

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgemeza.medidaexacta.client.domain.usecase.AddClientUseCase
import com.jorgemeza.medidaexacta.client.domain.usecase.GetClientByIdUseCase
import com.jorgemeza.medidaexacta.client.domain.model.ClientModel
import com.jorgemeza.medidaexacta.client.domain.usecase.GetAllClientUseCase
import com.jorgemeza.medidaexacta.client.ui.detail.ClientDetailEvent
import com.jorgemeza.medidaexacta.client.ui.detail.ClientDetailState
import com.jorgemeza.medidaexacta.quotation.domain.model.QuotationModel
import com.jorgemeza.medidaexacta.quotation.domain.usecase.AddQuotationUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GeneratePdfUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetQuotationByIdUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetQuotationConsecutiveUseCase
import com.jorgemeza.medidaexacta.quotation.ui.detail.QuotationDetailEvent
import com.jorgemeza.medidaexacta.quotation.ui.detail.QuotationDetailState
import com.jorgemeza.medidaexacta.shoppingCar.domain.usecase.GetDetailByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class InvoiceDetailViewModel  @Inject constructor(
    @ApplicationContext val context: Context,
    private val getQuotationByIdUseCase: GetQuotationByIdUseCase,
    private val getQuotationDetailByIdUseCase: GetDetailByIdUseCase,
    private val getAllClientsUseCase: GetAllClientUseCase,
    private val generatePdfUseCase: GeneratePdfUseCase
) : ViewModel() {

    lateinit var quotation: QuotationModel

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

    fun getInvoiceById(quotationId: String) {

        viewModelScope.launch {

            state = state.copy(
                isLoading = true
            )

            quotation = getQuotationByIdUseCase(quotationId)
            val products = getQuotationDetailByIdUseCase(quotationId)

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
            state = state.copy(
                isLoading = true
            )

            getAllClientsUseCase().collect { clients ->
                state = state.copy(
                    clients = clients,
                    isLoading = false
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
                generatePdfUseCase(
                    client = state.clients.firstOrNull { it.name == state.client }!!,
                    quotation = quotation,
                    state.products
                )
                state = state.copy(
                    isLoading = false
                )
            }
        }
    }

}