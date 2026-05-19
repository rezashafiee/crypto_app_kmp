package com.tilda.feature.news.data.mapper

import com.tilda.feature.news.data.dto.NewsArticleDto
import com.tilda.feature.news.data.local.NewsEntity
import com.tilda.feature.news.domain.model.NewsArticle
import kotlin.time.Clock
import kotlin.time.Instant

private const val CATEGORY_SEPARATOR = "||"

fun NewsArticleDto.toNewsArticle(): NewsArticle {
    val fallbackId = listOfNotNull(
        guid,
        id?.toString(),
        url,
        title
    ).firstOrNull().orEmpty()

    return NewsArticle(
        id = fallbackId,
        title = title.orEmpty(),
        body = body.orEmpty().trim(),
        authors = authors.orEmpty(),
        source = sourceData?.name.orEmpty().ifBlank { "CoinDesk" },
        url = url.orEmpty(),
        imageUrl = imageUrl.orEmpty(),
        publishedOn = Instant.fromEpochSeconds(publishedOn ?: 0L),
        categories = categoryData
            ?.mapNotNull { it.name ?: it.category }
            ?.filter { it.isNotBlank() }
            .orEmpty()
    )
}

fun NewsArticle.toNewsEntity(
    cachedAtEpochSeconds: Long = Clock.System.now().epochSeconds
): NewsEntity {
    return NewsEntity(
        id = id,
        title = title,
        body = body,
        authors = authors,
        source = source,
        url = url,
        imageUrl = imageUrl,
        publishedOnEpochSeconds = publishedOn.epochSeconds,
        categories = categories.joinToString(CATEGORY_SEPARATOR),
        cachedAtEpochSeconds = cachedAtEpochSeconds
    )
}

fun NewsEntity.toNewsArticle(): NewsArticle {
    return NewsArticle(
        id = id,
        title = title,
        body = body,
        authors = authors,
        source = source,
        url = url,
        imageUrl = imageUrl,
        publishedOn = Instant.fromEpochSeconds(publishedOnEpochSeconds),
        categories = categories
            .split(CATEGORY_SEPARATOR)
            .filter { it.isNotBlank() }
    )
}
