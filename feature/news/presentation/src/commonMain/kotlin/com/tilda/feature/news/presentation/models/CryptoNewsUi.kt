package com.tilda.feature.news.presentation.models

import androidx.compose.runtime.Immutable
import com.tilda.feature.news.domain.model.NewsArticle
import kotlin.time.Clock

@Immutable
data class CryptoNewsUi(
    val id: String,
    val title: String,
    val body: String,
    val excerpt: String,
    val authors: String,
    val source: String,
    val url: String,
    val imageUrl: String,
    val publishedOn: String,
    val categories: List<String>
)

fun NewsArticle.toCryptoNewsUi(): CryptoNewsUi {
    val normalizedBody = body.replace(Regex("\\s+"), " ").trim()
    val excerpt = if (normalizedBody.length > 180) {
        "${normalizedBody.take(180)}..."
    } else {
        normalizedBody
    }

    return CryptoNewsUi(
        id = id,
        title = title,
        body = body,
        excerpt = excerpt,
        authors = authors,
        source = source,
        url = url,
        imageUrl = imageUrl,
        publishedOn = if (publishedOn.epochSeconds > 0L) {
            publishedOn.toString()
                .replace("T", " ")
                .removeSuffix("Z")
                .take(16)
        } else {
            ""
        },
        categories = categories
    )
}

internal val previewNewsArticle = CryptoNewsUi(
    id = "preview-news",
    title = "Bitcoin Holds Its Range as Traders Watch ETF Flows",
    body = "Bitcoin traded in a narrow range while market participants watched fund flows, macro data, and liquidity conditions across major exchanges.",
    excerpt = "Bitcoin traded in a narrow range while market participants watched fund flows, macro data, and liquidity conditions across major exchanges.",
    authors = "CoinDesk Markets Team",
    source = "CoinDesk",
    url = "https://www.coindesk.com",
    imageUrl = "",
    publishedOn = Clock.System.now().toString()
        .replace("T", " ")
        .removeSuffix("Z")
        .take(16),
    categories = listOf("Markets", "Bitcoin")
)
