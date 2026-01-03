package com.tilda.crypto.data.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.RemoteMediator
import com.tilda.core.data.database.dao.CoinDao
import com.tilda.core.data.database.model.CoinEntity
import com.tilda.crypto.data.datasource.CoinLocalDataSource
import com.tilda.crypto.data.datasource.CoinRemoteDataSource
import com.tilda.crypto.data.local.CoinLocalDataSourceImp
import com.tilda.crypto.data.paging.CoinsRemoteMediator
import com.tilda.crypto.data.remote.CoinRemoteDataSourceImp
import com.tilda.crypto.data.repository.CoinRepositoryImp
import com.tilda.feature.crypto.domain.interactor.GetPagedCoinsUseCase
import com.tilda.feature.crypto.domain.repository.CoinRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

@OptIn(ExperimentalPagingApi::class)
val cryptoDataModule = module {
    singleOf(::CoinRemoteDataSourceImp) { bind<CoinRemoteDataSource>() }
    singleOf(::CoinLocalDataSourceImp) { bind<CoinLocalDataSource>() }
    singleOf(::CoinRepositoryImp) { bind<CoinRepository>() }
    singleOf(::CoinsRemoteMediator) { bind<RemoteMediator<Int, CoinEntity>>() }
    singleOf(::GetPagedCoinsUseCase)
    single { PagingConfig(pageSize = 50, initialLoadSize = 50) }
    factory {
        Pager(
            config = get(),
            remoteMediator = get<RemoteMediator<Int, CoinEntity>>(),
            pagingSourceFactory = {
                get<CoinDao>().getPagingSource()
            }
        )
    }
}