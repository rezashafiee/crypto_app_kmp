package com.tilda.feature.crypto.domain.model

import kotlin.time.Instant

data class CoinPrice(
    val openingPrice: Double,
    val highestPrice: Double,
    val lowestPrice: Double,
    val closingPrice: Double,
    val dateTime: Instant,
    val volume: Double
)
