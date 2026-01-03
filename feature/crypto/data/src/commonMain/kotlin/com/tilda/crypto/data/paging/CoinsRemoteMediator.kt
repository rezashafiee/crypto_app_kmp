package com.tilda.crypto.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.tilda.core.data.database.model.CoinEntity
import com.tilda.crypto.data.datasource.CoinLocalDataSource
import com.tilda.crypto.data.datasource.CoinRemoteDataSource
import com.tilda.crypto.data.mapper.toCoinEntity
import com.tilda.core.domain.Result
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalPagingApi::class)
class CoinsRemoteMediator(
    private val remoteDataSource: CoinRemoteDataSource,
    private val localDataSource: CoinLocalDataSource
) : RemoteMediator<Int, CoinEntity>() {

    private val sortBy = "TOTAL_MKT_CAP_USD"
    private val sortDirection = "DESC"

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CoinEntity>
    ): MediatorResult {

        val pageSize = state.config.pageSize

        return try {
            val pageNumber = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val totalItems = localDataSource.getItemsCount()
                    val nextPage = totalItems / pageSize + 1
                    nextPage
                }
            }

            when (val apiResult =
                remoteDataSource.getCoins(
                    pageSize = pageSize,
                    page = pageNumber,
                    sortBy = sortBy,
                    sortDirection = sortDirection
                )) {
                is Result.Success -> {
                    val coins = apiResult.data
                    val endOfPaginationReached = coins.isEmpty() || coins.size < pageSize
                    val coinEntities = coins.map { it.toCoinEntity() }

                    when (loadType) {
                        LoadType.REFRESH -> localDataSource.replaceAllCoins(coinEntities)
                        LoadType.APPEND -> localDataSource.addCoins(coinEntities)
                        else -> Unit
                    }
                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                }

                is Result.Error -> {
                    val error = apiResult.error
                    MediatorResult.Error(error)
                }
            }

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {

        ////////////////////////////
        val cacheTimeout = 1.hours
        ////////////////////////////

        val lastUpdatedTime = localDataSource.getLastUpdated()
        val currentTime = Clock.System.now().epochSeconds
        val passedTime = (currentTime - lastUpdatedTime).milliseconds

        return if (passedTime <= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }
}