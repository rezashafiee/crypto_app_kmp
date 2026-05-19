package com.tilda.feature.news.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    @SerialName("Data")
    val data: List<NewsArticleDto> = emptyList()
)

@Serializable
data class NewsArticleDto(
    @SerialName("ID")
    val id: Long? = null,
    @SerialName("GUID")
    val guid: String? = null,
    @SerialName("TITLE")
    val title: String? = null,
    @SerialName("BODY")
    val body: String? = null,
    @SerialName("AUTHORS")
    val authors: String? = null,
    @SerialName("URL")
    val url: String? = null,
    @SerialName("IMAGE_URL")
    val imageUrl: String? = null,
    @SerialName("PUBLISHED_ON")
    val publishedOn: Long? = null,
    @SerialName("SOURCE_DATA")
    val sourceData: NewsSourceDto? = null,
    @SerialName("CATEGORY_DATA")
    val categoryData: List<NewsCategoryDto>? = null
)

@Serializable
data class NewsSourceDto(
    @SerialName("NAME")
    val name: String? = null,
    @SerialName("URL")
    val url: String? = null
)

@Serializable
data class NewsCategoryDto(
    @SerialName("CATEGORY")
    val category: String? = null,
    @SerialName("NAME")
    val name: String? = null
)
