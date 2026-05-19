package com.tilda.feature.crypto.domain.repository

import androidx.paging.PagingData
import com.tilda.core.domain.NetworkError
import com.tilda.core.domain.Result
import com.tilda.feature.crypto.domain.model.Coin
import com.tilda.feature.crypto.domain.model.CoinPrice
import kotlinx.coroutines.flow.Flow
import kotlin.time.Instant

interface CoinRepository {
    fun getPagedCoins(): Flow<PagingData<Coin>>

    fun getFavoriteCoinIds(): Flow<Set<Int>>

    suspend fun setCoinFavorite(coinId: Int, isFavorite: Boolean)

    suspend fun getCoinsHistory(
        coinSymbol: String,
        end: Instant
    ): Result<List<CoinPrice>, NetworkError>
}
