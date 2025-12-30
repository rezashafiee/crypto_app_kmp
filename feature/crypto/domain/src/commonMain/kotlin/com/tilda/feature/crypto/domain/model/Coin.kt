package com.tilda.feature.crypto.domain.model

data class Coin(
    val id: Int,
    val rank: String,
    val symbol: String,
    val name: String,
    val currentPrice: Double,
    val marketCap: Double,
    val priceChange24h: Double,
    val priceChangePercentage24h: Double,
    val logoUrl: String,
    val lastUpdate: Long,
)