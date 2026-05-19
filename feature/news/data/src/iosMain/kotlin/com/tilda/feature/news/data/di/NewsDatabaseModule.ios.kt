package com.tilda.feature.news.data.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.tilda.feature.news.data.local.NewsDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual val platformNewsDatabaseModule: Module
    get() = module {
        single<RoomDatabase.Builder<NewsDatabase>>(named("newsDatabaseBuilder")) {
            val dbFilePath = newsDocumentDirectory() + "/news_database.db"
            Room.databaseBuilder<NewsDatabase>(
                dbFilePath
            )
        }
    }

@OptIn(ExperimentalForeignApi::class)
private fun newsDocumentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}
