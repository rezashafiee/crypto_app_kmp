package com.tilda.crypto.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinListResponse(
    @SerialName("Data")
    val data: Data
)

@Serializable
data class Data(
    @SerialName("LIST")
    val list: List<CoinDto>
)

