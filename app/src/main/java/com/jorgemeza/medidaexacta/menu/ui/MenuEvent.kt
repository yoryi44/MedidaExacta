package com.jorgemeza.medidaexacta.menu.ui

sealed interface MenuEvent {
    data class onItemSelect(val destination: String) : MenuEvent
}