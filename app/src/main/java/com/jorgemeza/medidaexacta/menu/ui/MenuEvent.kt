package com.jorgemeza.medidaexacta.menu.ui

sealed interface MenuEvent {
    data class OnItemSelect(val destination: String) : MenuEvent
}