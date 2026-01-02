package com.tilda.crypto.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinDto(
    @SerialName("ID")
    val id: Int,
    @SerialName("TOPLIST_BASE_RANK")
    val rank: Rank,
    @SerialName("SYMBOL")
    val symbol: String,
    @SerialName("NAME")
    val name: String,
    @SerialName("PRICE_USD")
    val priceUsd: Double,
    @SerialName("TOTAL_MKT_CAP_USD")
    val marketCapUsd: Double,
    @SerialName("SPOT_MOVING_24_HOUR_CHANGE_USD")
    val change24Hr: Double,
    @SerialName("SPOT_MOVING_24_HOUR_CHANGE_PERCENTAGE_USD")
    val changePercent24Hr: Double,
    @SerialName("LOGO_URL")
    val logoUrl: String?,
    @SerialName("ASSET_DESCRIPTION_SNIPPET")
    val description: String?,
    @SerialName("PRICE_USD_LAST_UPDATE_TS")
    val lastUpdate: Long,
)

@Serializable
data class Rank(
    @SerialName("TOTAL_MKT_CAP_USD")
    val marketCapRank: Short
)