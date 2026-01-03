package com.tilda.core.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.tilda.core.data.database.dao.CoinDao
import com.tilda.core.data.database.model.CoinEntity

@Database(entities = [CoinEntity::class], version = 1)
@ConstructedBy(CoinDatabaseConstructor::class)
abstract class CoinDatabase: RoomDatabase() {
    abstract fun coinDao(): CoinDao
}