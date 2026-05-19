package com.tilda.feature.news.data.remote

import com.tilda.core.data.network.constructUrl
import com.tilda.core.data.network.safeCall
import com.tilda.core.domain.NetworkError
import com.tilda.core.domain.Result
import com.tilda.core.domain.map
import com.tilda.feature.news.data.datasource.NewsRemoteDataSource
import com.tilda.feature.news.data.dto.NewsResponse
import com.tilda.feature.news.data.mapper.toNewsArticle
import com.tilda.feature.news.domain.model.NewsArticle
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class NewsRemoteDataSourceImp(
    private val httpClient: HttpClient
) : NewsRemoteDataSource {

    override suspend fun getNews(
        limit: Int,
        toTs: Long?
    ): Result<List<NewsArticle>, NetworkError> {
        return safeCall<NewsResponse> {
            httpClient.get(urlString = constructUrl("/news/v1/article/list")) {
                url.parameters.append("lang", "EN")
                url.parameters.append("limit", limit.toString())
                toTs?.let {
                    url.parameters.append("to_ts", it.toString())
                }
            }
        }.map { response ->
            response.data
                .map { it.toNewsArticle() }
                .filter { it.title.isNotBlank() }
        }
    }
}
