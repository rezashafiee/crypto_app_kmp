package com.tilda.feature.news.data.datasource

import com.tilda.core.domain.NetworkError
import com.tilda.core.domain.Result
import com.tilda.feature.news.domain.model.NewsArticle

interface NewsRemoteDataSource {
    suspend fun getNews(
        limit: Int,
        toTs: Long? = null
    ): Result<List<NewsArticle>, NetworkError>
}
