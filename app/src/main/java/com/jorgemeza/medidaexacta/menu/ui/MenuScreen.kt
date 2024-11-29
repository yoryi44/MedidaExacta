package com.jorgemeza.medidaexacta.menu.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorgemeza.medidaexacta.menu.ui.components.MenuItemComponent
import com.jorgemeza.medidaexacta.menu.ui.models.MenuItem
import com.jorgemeza.medidaexacta.navigation.Client
import com.jorgemeza.medidaexacta.navigation.Invoice
import com.jorgemeza.medidaexacta.navigation.Quotation
import com.jorgemeza.medidaexacta.ui.theme.LigthGray
import com.jorgemeza.medidaexacta.ui.theme.WarmGray

@Composable
fun MenuScreeen(
    menuViewModel: MenuViewModel = hiltViewModel(),
    onNavigate: (Any) -> Unit
) {

    val menuItems = listOf(
        MenuItem(item = "Clientes", icon = Icons.Default.AccountCircle, navigate = Client),
        MenuItem(item = "Cotizaciones", icon = Icons.Default.DateRange, navigate = Quotation),
        MenuItem(item = "Facturas", icon = Icons.Default.ShoppingCart, navigate = Invoice),
    )

    val state = menuViewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Dos columnas
            modifier = Modifier.fillMaxSize()
        ) {
            items(menuItems) { item ->
                MenuItemComponent(item = item.item, icon = item.icon) {
                    onNavigate(item.navigate)
                }
            }
        }

    }

}