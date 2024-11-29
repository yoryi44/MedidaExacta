package com.jorgemeza.medidaexacta.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.jorgemeza.medidaexacta.client.ui.detail.ClientDetailScreen
import com.jorgemeza.medidaexacta.client.ui.list.ClientScreen
import com.jorgemeza.medidaexacta.invoice.ui.InvoiceScreen
import com.jorgemeza.medidaexacta.menu.ui.MenuScreeen
import com.jorgemeza.medidaexacta.quotation.ui.QuotationScreen

@Composable
fun NavigationHost(
    navHostController: NavHostController,
    startDestination: Any,
) {
    NavHost(navController = navHostController, startDestination = startDestination) {

        //Menu
        composable<Menu> {
            MenuScreeen {
                navHostController.navigate(it)
            }
        }

        //Client
        composable<Client> {
            ClientScreen() {
                navHostController.navigate(ClientDetail(clientId = it))
            }
        }

        //Quotation
        composable<Quotation> {
            QuotationScreen()
        }

        //Invoice
        composable<Invoice> {
            InvoiceScreen()
        }

        //Detail
        composable<ClientDetail> {
            val detail = it.toRoute<ClientDetail>()
            ClientDetailScreen(detail.clientId) {
                navHostController.popBackStack()
            }
        }

    }
}