package com.tilda.feature.news.data.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.tilda.feature.news.data.local.NewsDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val platformNewsDatabaseModule: Module
    get() = module {
        single<RoomDatabase.Builder<NewsDatabase>>(named("newsDatabaseBuilder")) {
            Room.databaseBuilder(
                androidApplication().applicationContext,
                NewsDatabase::class.java,
                "news_database"
            )
        }
    }
