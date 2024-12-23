package com.jorgemeza.medidaexacta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgemeza.medidaexacta.client.domain.usecase.GetAllClientMainUseCase
import com.jorgemeza.medidaexacta.quotation.domain.usecase.GetAllQuotationMainUseCase
import com.jorgemeza.medidaexacta.shoppingCar.domain.usecase.GetAllQuotationDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllCLientMainUseCase: GetAllClientMainUseCase,
    private val getAllQuotationMainUseCase: GetAllQuotationMainUseCase,
    private val getAllQuotationDetailUseCase: GetAllQuotationDetailUseCase
) : ViewModel() {

    var isLoading = true

    init {
        viewModelScope.launch {
            getAllCLientMainUseCase()
            getAllQuotationMainUseCase()
            getAllQuotationDetailUseCase()
            isLoading = false
        }
    }

}