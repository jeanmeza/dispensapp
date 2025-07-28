package com.jeanmeza.dispensapp.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkItem(
    val name: String,
    @SerialName("measure_unit")
    val measureUnit: String,
)
