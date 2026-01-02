package com.tilda.crypto.data.datasource

import com.tilda.core.domain.NetworkError
import com.tilda.core.domain.Result
import com.tilda.feature.crypto.domain.model.Coin
import com.tilda.feature.crypto.domain.model.CoinPrice
import kotlin.time.Instant

interface CoinRemoteDataSource {
    suspend fun getCoins(
        pageSize: Int,
        page: Int,
        sortBy: String,
        sortDirection: String
    ): Result<List<Coin>, NetworkError>

    suspend fun getCoinHistory(
        coinSymbol: String,
        end: Instant
    ): Result<List<CoinPrice>, NetworkError>
}