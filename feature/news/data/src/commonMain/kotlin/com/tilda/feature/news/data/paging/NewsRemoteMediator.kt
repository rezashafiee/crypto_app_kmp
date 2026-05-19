package com.tilda.feature.news.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.tilda.core.domain.Result
import com.tilda.feature.news.data.datasource.NewsLocalDataSource
import com.tilda.feature.news.data.datasource.NewsRemoteDataSource
import com.tilda.feature.news.data.local.NewsEntity
import com.tilda.feature.news.data.mapper.toNewsEntity
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val remoteDataSource: NewsRemoteDataSource,
    private val localDataSource: NewsLocalDataSource
) : RemoteMediator<Int, NewsEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsEntity>
    ): MediatorResult {
        return try {
            val pageSize = state.config.pageSize
            val toTs = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> localDataSource.getOldestPublishedOn()?.minus(1)
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
            }

            when (val apiResult = remoteDataSource.getNews(limit = pageSize, toTs = toTs)) {
                is Result.Success -> {
                    val articles = apiResult.data
                    val previousCount = localDataSource.getItemsCount()
                    val entities = articles.map { it.toNewsEntity() }

                    when (loadType) {
                        LoadType.REFRESH -> localDataSource.replaceAllNews(entities)
                        LoadType.APPEND -> localDataSource.addNews(entities)
                        LoadType.PREPEND -> Unit
                    }

                    val newCount = localDataSource.getItemsCount()
                    val insertedNewItems = newCount > previousCount
                    val endReached = articles.isEmpty() ||
                            articles.size < pageSize ||
                            (loadType == LoadType.APPEND && !insertedNewItems)

                    MediatorResult.Success(endOfPaginationReached = endReached)
                }

                is Result.Error -> MediatorResult.Error(apiResult.error)
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val lastUpdated = localDataSource.getLastUpdated()
            ?: return InitializeAction.LAUNCH_INITIAL_REFRESH
        val passedTime = (Clock.System.now().epochSeconds - lastUpdated).seconds

        return if (passedTime <= 1.hours) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }
}
