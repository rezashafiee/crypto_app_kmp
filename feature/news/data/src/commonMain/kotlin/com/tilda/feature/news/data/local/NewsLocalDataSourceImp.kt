package com.tilda.feature.news.data.local

import androidx.room.immediateTransaction
import androidx.room.useWriterConnection
import com.tilda.feature.news.data.datasource.NewsLocalDataSource

class NewsLocalDataSourceImp(
    private val newsDatabase: NewsDatabase
) : NewsLocalDataSource {

    private val newsDao = newsDatabase.newsDao()

    override suspend fun getItemsCount(): Int {
        return newsDao.getNewsCount()
    }

    override suspend fun getLastUpdated(): Long? {
        return newsDao.getLastUpdatedEpochSeconds()
    }

    override suspend fun getOldestPublishedOn(): Long? {
        return newsDao.getOldestPublishedOnEpochSeconds()
    }

    override suspend fun replaceAllNews(newsEntities: List<NewsEntity>) {
        newsDatabase.useWriterConnection { transactor ->
            transactor.immediateTransaction {
                newsDao.deleteAllNews()
                newsDao.insertNews(*newsEntities.toTypedArray())
            }
        }
    }

    override suspend fun addNews(newsEntities: List<NewsEntity>) {
        newsDao.insertNews(*newsEntities.toTypedArray())
    }
}
