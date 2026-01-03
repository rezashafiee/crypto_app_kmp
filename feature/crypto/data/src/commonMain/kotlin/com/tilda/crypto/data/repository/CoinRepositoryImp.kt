package com.tilda.crypto.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.tilda.core.data.database.model.CoinEntity
import com.tilda.core.domain.NetworkError
import com.tilda.core.domain.Result
import com.tilda.crypto.data.datasource.CoinRemoteDataSource
import com.tilda.crypto.data.mapper.toCoin
import com.tilda.feature.crypto.domain.model.Coin
import com.tilda.feature.crypto.domain.model.CoinPrice
import com.tilda.feature.crypto.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Instant


class CoinRepositoryImp(
    private val pager: Pager<Int, CoinEntity>,
    private val coinRemoteDataSource: CoinRemoteDataSource
) : CoinRepository {

    override fun getPagedCoins(): Flow<PagingData<Coin>> {
        return pager.flow.map { pagingData ->
            pagingData.map { coinEntity -> coinEntity.toCoin() }
        }
    }

    override suspend fun getCoinsHistory(
        coinSymbol: String,
        end: Instant
    ): Result<List<CoinPrice>, NetworkError> {
        return coinRemoteDataSource.getCoinHistory(coinSymbol, end)
    }
}