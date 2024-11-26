package com.jorgemeza.medidaexacta.menu.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MenuViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(MenuState())
        private set


}