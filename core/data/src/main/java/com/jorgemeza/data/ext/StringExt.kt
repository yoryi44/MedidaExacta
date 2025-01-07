package com.jorgemeza.data.ext

import android.util.Log
import java.text.NumberFormat
import java.util.Locale

fun String.toPrice(): String {
    return try {
        val valor = this.toDouble()
        val format = NumberFormat.getCurrencyInstance(Locale("es", "ES"))
        format.format(valor)
    } catch (e: NumberFormatException) {
        Log.e("toPrice", e.message.toString())
        return e.message.toString()
    }
}