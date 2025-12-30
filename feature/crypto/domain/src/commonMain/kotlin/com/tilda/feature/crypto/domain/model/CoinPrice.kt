package com.tilda.feature.crypto.domain.model

import java.time.ZonedDateTime

data class CoinPrice(
    val openingPrice: Double,
    val highestPrice: Double,
    val lowestPrice: Double,
    val closingPrice: Double,
    val dateTime: ZonedDateTime,
    val volume: Double
)
