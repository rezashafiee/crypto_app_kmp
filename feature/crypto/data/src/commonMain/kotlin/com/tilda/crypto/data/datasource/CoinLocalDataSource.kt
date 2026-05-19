package com.tilda.crypto.data.datasource

import com.tilda.core.data.database.model.CoinEntity
import kotlinx.coroutines.flow.Flow

interface CoinLocalDataSource {
    suspend fun getItemsCount(): Int

    suspend fun getLastUpdated(): Long

    suspend fun replaceAllCoins(coinEntities: List<CoinEntity>)

    suspend fun addCoins(coinEntities: List<CoinEntity>)

    fun getFavoriteCoinIds(): Flow<List<Int>>

    suspend fun addFavoriteCoin(coinId: Int)

    suspend fun deleteFavoriteCoin(coinId: Int)
}
