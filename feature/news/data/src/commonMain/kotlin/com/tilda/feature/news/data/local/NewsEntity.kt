package com.tilda.feature.news.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_articles")
data class NewsEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val body: String,
    val authors: String,
    val source: String,
    val url: String,
    val imageUrl: String,
    val publishedOnEpochSeconds: Long,
    val categories: String,
    val cachedAtEpochSeconds: Long
)
