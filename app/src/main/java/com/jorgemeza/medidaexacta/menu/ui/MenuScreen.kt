package com.jorgemeza.medidaexacta.menu.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorgemeza.medidaexacta.menu.ui.components.MenuItemComponent
import com.jorgemeza.medidaexacta.navigation.Client
import com.jorgemeza.medidaexacta.ui.theme.CoffeeYellow80

@Composable
fun MenuScreeen(
    menuViewModel: MenuViewModel = hiltViewModel(),
    onNavigate: (Any) -> Unit
) {

    val state = menuViewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CoffeeYellow80.copy(alpha = 0.5f))
            .padding(16.dp),
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            MenuItemComponent(item = "Clientes") {
                onNavigate(Client)
            }

            MenuItemComponent(item = "Clientes") {
                onNavigate(Client)
            }

        }

    }

}