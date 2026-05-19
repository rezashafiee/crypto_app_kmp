package com.tilda.core.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.tilda.core.data.database.dao.CoinDao
import com.tilda.core.data.database.dao.FavoriteCoinDao
import com.tilda.core.data.database.model.CoinEntity
import com.tilda.core.data.database.model.FavoriteCoinEntity

@Database(
    entities = [
        CoinEntity::class,
        FavoriteCoinEntity::class
    ],
    version = 2
)
@ConstructedBy(CoinDatabaseConstructor::class)
abstract class CoinDatabase: RoomDatabase() {
    abstract fun coinDao(): CoinDao
    abstract fun favoriteCoinDao(): FavoriteCoinDao
}
