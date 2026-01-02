package com.tilda.core.data.db.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.tilda.core.data.db.CoinDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDatabaseModule: Module
    get() = module {
        single<RoomDatabase.Builder<CoinDatabase>> {

            Room.databaseBuilder(
                androidApplication().applicationContext,
                CoinDatabase::class.java,
                "coin_database"
            )
        }
    }