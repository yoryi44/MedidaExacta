package com.jorgemeza.medidaexacta.shoppingCar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DetailEntity (
    @PrimaryKey(autoGenerate = false)
    val id : String,
    val quotation: String,
    val amount : String,
    val price : String,
    val product : String,
)