package com.tilda.core.data.db.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.tilda.core.data.db.CoinDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual val platformDatabaseModule: Module
    get() = module {
        single<RoomDatabase.Builder<CoinDatabase>> {
            val dbFilePath = documentDirectory() + "/coin_database.db"
            Room.databaseBuilder<CoinDatabase>(
                dbFilePath
            )
        }

    }


@OptIn(ExperimentalForeignApi::class)
fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}