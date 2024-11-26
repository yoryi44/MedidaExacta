package com.jorgemeza.medidaexacta.menu.ui

import com.jorgemeza.medidaexacta.navigation.Menu

data class MenuState(
    val menuItems: List<String> = listOf(),
    val route: Any? = null
)
