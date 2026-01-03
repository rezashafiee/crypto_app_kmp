package com.tilda.core.data.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.tilda.core.data.database.CoinDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformDatabaseModule: Module

val databaseModule = module {
    includes(platformDatabaseModule)

    single {
        get<RoomDatabase.Builder<CoinDatabase>>()
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
    single { get<CoinDatabase>().coinDao() }
}