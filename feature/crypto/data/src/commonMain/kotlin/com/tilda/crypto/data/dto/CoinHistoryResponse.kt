package com.tilda.crypto.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinHistoryResponse(
    @SerialName("Data")
    val data: List<CoinPriceDto>
)