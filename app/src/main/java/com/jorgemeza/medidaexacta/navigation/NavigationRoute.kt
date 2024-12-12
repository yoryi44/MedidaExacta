package com.jorgemeza.medidaexacta.navigation

import kotlinx.serialization.Serializable

@Serializable
object Menu

@Serializable
object Client

@Serializable
object Quotation

@Serializable
object Invoice

@Serializable
data class ClientDetail(val id: String?)

@Serializable
data class QuotationDetail(val id: String?)

@Serializable
data class ShoppingCar(val id: String?)

@Serializable
data class ShoppingCarDetail(val id: String?, val quotation: String?)