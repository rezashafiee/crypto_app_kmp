package com.tilda.crypto.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinPriceDto(
    @SerialName("CLOSE")
    val closingPrice: Double,
    @SerialName("OPEN")
    val openingPrice: Double,
    @SerialName("HIGH")
    val highestPrice: Double,
    @SerialName("LOW")
    val lowestPrice: Double,
    @SerialName("TIMESTAMP")
    val timestamp: Long,
    @SerialName("VOLUME")
    val volume: Double
)
