package com.tilda.feature.news.data.datasource

import com.tilda.feature.news.data.local.NewsEntity

interface NewsLocalDataSource {
    suspend fun getItemsCount(): Int

    suspend fun getLastUpdated(): Long?

    suspend fun getOldestPublishedOn(): Long?

    suspend fun replaceAllNews(newsEntities: List<NewsEntity>)

    suspend fun addNews(newsEntities: List<NewsEntity>)
}
