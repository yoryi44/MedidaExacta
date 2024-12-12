package com.jorgemeza.medidaexacta.shoppingCar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DetailSyncEntity (
    @PrimaryKey(autoGenerate = false)
    val id : String
)