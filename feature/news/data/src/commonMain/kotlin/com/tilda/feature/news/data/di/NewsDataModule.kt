package com.tilda.feature.news.data.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.tilda.feature.news.data.datasource.NewsLocalDataSource
import com.tilda.feature.news.data.datasource.NewsRemoteDataSource
import com.tilda.feature.news.data.local.NewsDao
import com.tilda.feature.news.data.local.NewsDatabase
import com.tilda.feature.news.data.local.NewsEntity
import com.tilda.feature.news.data.local.NewsLocalDataSourceImp
import com.tilda.feature.news.data.paging.NewsRemoteMediator
import com.tilda.feature.news.data.remote.NewsRemoteDataSourceImp
import com.tilda.feature.news.data.repository.NewsRepositoryImp
import com.tilda.feature.news.domain.interactor.GetNewsUseCase
import com.tilda.feature.news.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

expect val platformNewsDatabaseModule: Module

@OptIn(ExperimentalPagingApi::class)
val newsDataModule = module {
    includes(platformNewsDatabaseModule)

    single {
        get<RoomDatabase.Builder<NewsDatabase>>(named("newsDatabaseBuilder"))
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
    single<NewsDao> { get<NewsDatabase>().newsDao() }
    singleOf(::NewsRemoteDataSourceImp) { bind<NewsRemoteDataSource>() }
    singleOf(::NewsLocalDataSourceImp) { bind<NewsLocalDataSource>() }
    singleOf(::NewsRemoteMediator)
    single<NewsRepository> {
        NewsRepositoryImp(
            pager = get(named("newsPager"))
        )
    }
    singleOf(::GetNewsUseCase)
    single(named("newsPagingConfig")) { PagingConfig(pageSize = 30, initialLoadSize = 30) }
    single(named("newsPager")) {
        Pager(
            config = get(named("newsPagingConfig")),
            remoteMediator = get<NewsRemoteMediator>(),
            pagingSourceFactory = {
                get<NewsDao>().getPagingSource()
            }
        )
    }
}
