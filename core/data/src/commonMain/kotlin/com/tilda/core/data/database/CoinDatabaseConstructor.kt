package com.tilda.core.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object CoinDatabaseConstructor: RoomDatabaseConstructor<CoinDatabase> {
    override fun initialize(): CoinDatabase
}