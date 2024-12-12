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
import com.jorgemeza.medidaexacta.quotation.ui.detail.QuotationDetailScreen
import com.jorgemeza.medidaexacta.quotation.ui.list.QuotationScreen
import com.jorgemeza.medidaexacta.shoppingCar.ui.detail.ShopingCarDetailScreen
import com.jorgemeza.medidaexacta.shoppingCar.ui.list.ShoppingCarScreen

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
            ClientScreen {
                navHostController.navigate(ClientDetail(id = it))
            }
        }

        //Quotation
        composable<Quotation> {
            QuotationScreen {
                navHostController.navigate(QuotationDetail(id = it))
            }
        }

        //Invoice
        composable<Invoice> {
            InvoiceScreen()
        }

        //Client Detail
        composable<ClientDetail> {
            val detail = it.toRoute<ClientDetail>()
            ClientDetailScreen(detail.id) {
                navHostController.popBackStack()
            }
        }

        //Quotation Detail
        composable<QuotationDetail> {
            val quotation = it.toRoute<QuotationDetail>()
            QuotationDetailScreen(
                onSaved = {
                    navHostController.popBackStack()
                },
                onShoppingCar = { id,quotation ->
                    navHostController.navigate(ShoppingCar(id = id))
                },
                quotationId = quotation.id
            )
        }

        //Shopping Car
        composable<ShoppingCar> {
            val quotation = it.toRoute<ShoppingCar>()
            ShoppingCarScreen (
                quotation.id,
                onDetail = {
                    navHostController.navigate(ShoppingCarDetail(id = it,quotation = quotation.id))
                }
            )
        }

        //Shopping Car Detail
        composable<ShoppingCarDetail> {
            val detail = it.toRoute<ShoppingCarDetail>()
            ShopingCarDetailScreen(detailId = detail.id,quotationId = detail.quotation) {
                navHostController.popBackStack()
            }
        }

    }
}