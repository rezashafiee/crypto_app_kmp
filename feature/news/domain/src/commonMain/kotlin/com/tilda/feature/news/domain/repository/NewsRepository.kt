package com.tilda.feature.news.domain.repository

import androidx.paging.PagingData
import com.tilda.feature.news.domain.model.NewsArticle
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getPagedNews(): Flow<PagingData<NewsArticle>>
}
