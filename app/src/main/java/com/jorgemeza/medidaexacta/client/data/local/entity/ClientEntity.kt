package com.jorgemeza.medidaexacta.client.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ClientEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val address: String,
    val postalCode: String,
    val name: String,
    val phone: String,
    val cif: String?,
    val email: String?
)
