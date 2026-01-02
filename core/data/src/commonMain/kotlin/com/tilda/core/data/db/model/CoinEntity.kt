package com.tilda.core.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class CoinEntity(
    @PrimaryKey(autoGenerate = true)
    val databaseId: Int = 0,
    val id: Int,
    val rank: String,
    val symbol: String,
    val name: String,
    val currentPrice: Double,
    val marketCap: Double,
    val priceChange24h: Double,
    val priceChangePercentage24h: Double,
    val logoUrl: String,
    val lastUpdate: Long
)