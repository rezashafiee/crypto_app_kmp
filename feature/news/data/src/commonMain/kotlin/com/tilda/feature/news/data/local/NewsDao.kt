package com.tilda.feature.news.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Query("SELECT * FROM news_articles ORDER BY publishedOnEpochSeconds DESC, id DESC")
    fun getPagingSource(): PagingSource<Int, NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(vararg news: NewsEntity)

    @Query("DELETE FROM news_articles")
    suspend fun deleteAllNews()

    @Query("SELECT COUNT(*) FROM news_articles")
    suspend fun getNewsCount(): Int

    @Query("SELECT MAX(cachedAtEpochSeconds) FROM news_articles")
    suspend fun getLastUpdatedEpochSeconds(): Long?

    @Query("SELECT MIN(publishedOnEpochSeconds) FROM news_articles")
    suspend fun getOldestPublishedOnEpochSeconds(): Long?
}
