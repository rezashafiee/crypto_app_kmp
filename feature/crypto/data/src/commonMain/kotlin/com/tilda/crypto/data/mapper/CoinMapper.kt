package com.tilda.crypto.data.mapper

import com.tilda.core.data.database.model.CoinEntity
import com.tilda.crypto.data.dto.CoinDto
import com.tilda.crypto.data.dto.CoinPriceDto
import com.tilda.feature.crypto.domain.model.Coin
import com.tilda.feature.crypto.domain.model.CoinPrice
import kotlin.time.Instant

fun CoinDto.toCoin() = Coin(
    id = id,
    name = name,
    currentPrice = priceUsd,
    priceChangePercentage24h = changePercent24Hr,
    priceChange24h = change24Hr,
    marketCap = marketCapUsd,
    rank = rank.marketCapRank.toString(),
    symbol = symbol,
    logoUrl = logoUrl ?: "",
    lastUpdate = lastUpdate
)

fun CoinEntity.toCoin() = Coin(
    id = id,
    name = name,
    currentPrice = currentPrice,
    priceChangePercentage24h = priceChangePercentage24h,
    priceChange24h = priceChange24h,
    marketCap = marketCap,
    rank = rank,
    symbol = symbol,
    logoUrl = logoUrl,
    lastUpdate = lastUpdate
)

fun Coin.toCoinEntity() = CoinEntity(
    id = id,
    name = name,
    currentPrice = currentPrice,
    priceChangePercentage24h = priceChangePercentage24h,
    priceChange24h = priceChange24h,
    marketCap = marketCap,
    rank = rank,
    symbol = symbol,
    logoUrl = logoUrl,
    lastUpdate = lastUpdate
)


fun CoinPriceDto.toCoinPrice(): CoinPrice {
    return CoinPrice(
        openingPrice = openingPrice,
        closingPrice = closingPrice,
        highestPrice = highestPrice,
        lowestPrice = lowestPrice,
        volume = volume,
        dateTime = Instant
            .fromEpochSeconds(timestamp)
    )
}