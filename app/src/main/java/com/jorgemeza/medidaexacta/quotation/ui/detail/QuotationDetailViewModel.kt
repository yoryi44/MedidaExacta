package com.jorgemeza.medidaexacta.quotation.ui.detail

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgemeza.medidaexacta.client.domain.usecase.GetAllClientUseCase
import com.jorgemeza.medidaexacta.quotation.domain.model.QuotationModel
import com.jorgemeza.medidaexacta.quotation.domain.usecase.AddQuotationUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GeneratePdfUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetAllQuotationDetailUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetQuotationByIdUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetQuotationConsecutiveUseCase
import com.jorgemeza.medidaexacta.shoppingCar.domain.usecase.GetDetailByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class QuotationDetailViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val addQuotationUserCase: AddQuotationUseCase,
    private val getQuotationByIdUseCase: GetQuotationByIdUseCase,
    private val getQuotationDetailByIdUseCase: GetDetailByIdUseCase,
    private val getAllQuotationDetailUseCase: GetAllQuotationDetailUseCase,
    private val getAllClientsUseCase: GetAllClientUseCase,
    private val generatePdfUseCase: GeneratePdfUseCase,
    private val getQuotationConsecutiveUseCase: GetQuotationConsecutiveUseCase
) : ViewModel() {

    lateinit var quotation: QuotationModel
    private var currentDayJob: Job? = null
    var state by mutableStateOf(QuotationDetailState())
        private set

    init {
        viewModelScope.launch {
            getAllQuotationDetailUseCase()
            getAllClients()
        }
    }

    fun onEvent(event: QuotationDetailEvent) {
        when (event) {

            is QuotationDetailEvent.OnClientChange -> {
                state = state.copy(client = event.client)
            }

            QuotationDetailEvent.OnSave -> {
                saveQuotation()
            }

            QuotationDetailEvent.OnPdf -> createPDF()
            QuotationDetailEvent.OnDismissDialog -> {
                state = state.copy(
                    error = null,
                )
            }

        }
    }

    private fun saveQuotation() {

        if(validateForm())
        {
            viewModelScope.launch {
                val quotation = QuotationModel(
                    id = state.id ?: UUID.randomUUID().toString(),
                    client = state.clients.firstOrNull { it.name == state.client }!!.id,
                    quotationNumber = state.quotationNumber,
                    price = state.price,
                    date = state.date,
                )

                addQuotationUserCase(quotation)
            }
            state = state.copy(
                isSaveSuccessful = true
            )
        }

    }

    private fun validateForm(): Boolean {
        var validate = true

        if(state.client.isBlank())
        {
            state = state.copy(error = "Choose an option")
            validate = false
        }

        return validate
    }

    fun createQuotation() {

        viewModelScope.launch {
            val consecutive = getQuotationConsecutiveUseCase()

            state = state.copy(
                quotationNumber = consecutive,
                date = LocalDate.now().toString(),
                isLoading = false
            )
        }
    }

    fun getQuotationById(quotationId: String) {

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

    private suspend fun getAllClients() {
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

    private fun createPDF() {

        if (!state.id.isNullOrEmpty()) {
            currentDayJob?.cancel()
            currentDayJob = viewModelScope.launch {
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