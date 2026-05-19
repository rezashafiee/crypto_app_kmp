package com.tilda.feature.news.presentation.news

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.tilda.core.domain.DomainError
import com.tilda.core.presentation.components.LoadingView
import com.tilda.core.presentation.util.toUiText
import com.tilda.feature.news.presentation.models.CryptoNewsUi
import com.tilda.feature.news.presentation.models.previewNewsArticle
import kotlinx.coroutines.flow.flowOf

@Composable
fun CryptoNewsScreen(
    state: CryptoNewsUiState,
    onArticleClick: (CryptoNewsUi) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pagedArticles = state.pagedArticles.collectAsLazyPagingItems()
    val refreshError = pagedArticles.loadState.refresh as? LoadState.Error
    val appendError = pagedArticles.loadState.append as? LoadState.Error

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainer)
    ) {
        val useListDetail = maxWidth >= 700.dp

        when {
            pagedArticles.loadState.refresh is LoadState.Loading && pagedArticles.itemCount == 0 -> {
                LoadingView()
            }

            refreshError != null && pagedArticles.itemCount == 0 -> {
                NewsErrorView(
                    error = refreshError.error,
                    onRetryClick = pagedArticles::retry,
                    modifier = Modifier.fillMaxSize()
                )
            }

            useListDetail -> {
                val selectedArticle = state.selectedArticle
                    ?: pagedArticles.itemSnapshotList.items.firstOrNull()
                val listWidth = if (maxWidth < 840.dp) 320.dp else 380.dp

                Row(modifier = Modifier.fillMaxSize()) {
                    NewsTitleList(
                        articles = pagedArticles,
                        selectedArticleId = selectedArticle?.id,
                        error = appendError?.error ?: refreshError?.error,
                        onArticleClick = onArticleClick,
                        onRefreshClick = pagedArticles::refresh,
                        onRetryClick = pagedArticles::retry,
                        modifier = Modifier
                            .width(listWidth)
                            .fillMaxHeight()
                    )
                    VerticalDivider(
                        modifier = Modifier
                            .width(1.dp)
                            .fillMaxHeight(),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                    NewsArticleDetail(
                        article = selectedArticle,
                        showBack = false,
                        onBackClick = onBackClick,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            state.selectedArticle != null -> {
                NewsArticleDetail(
                    article = state.selectedArticle,
                    showBack = true,
                    onBackClick = onBackClick,
                    modifier = Modifier.fillMaxSize()
                )
            }

            else -> {
                NewsTitleList(
                    articles = pagedArticles,
                    selectedArticleId = null,
                    error = appendError?.error ?: refreshError?.error,
                    onArticleClick = onArticleClick,
                    onRefreshClick = pagedArticles::refresh,
                    onRetryClick = pagedArticles::retry,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        if (pagedArticles.loadState.refresh is LoadState.Loading && pagedArticles.itemCount > 0) {
            LinearProgressIndicator(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun NewsTitleList(
    articles: LazyPagingItems<CryptoNewsUi>,
    selectedArticleId: String?,
    error: Throwable?,
    onArticleClick: (CryptoNewsUi) -> Unit,
    onRefreshClick: () -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceContainer)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "News",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.weight(1f)
                )
                TextButton(onClick = onRefreshClick) {
                    Text("Refresh")
                }
            }
        }

        if (error != null && articles.itemCount > 0) {
            item {
                NewsErrorBanner(
                    error = error,
                    onRetryClick = onRetryClick
                )
            }
        }

        items(articles.itemCount) { index ->
            articles[index]?.let { article ->
                NewsTitleListItem(
                    article = article,
                    isSelected = article.id == selectedArticleId,
                    onClick = { onArticleClick(article) }
                )
            }
        }

        if (articles.loadState.append is LoadState.Loading) {
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
private fun NewsTitleListItem(
    article: CryptoNewsUi,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 3.dp else 1.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(12.dp)
        ) {
            NewsArticleThumbnail(
                article = article,
                modifier = Modifier.size(96.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = article.title,
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = listOf(article.source, article.publishedOn)
                        .filter { it.isNotBlank() }
                        .joinToString(" - "),
                    color = MaterialTheme.colorScheme.outline,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (article.excerpt.isNotBlank()) {
                    Text(
                        text = article.excerpt,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun NewsArticleThumbnail(
    article: CryptoNewsUi,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
    ) {
        if (article.imageUrl.isNotBlank()) {
            AsyncImage(
                model = article.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun NewsArticleDetail(
    article: CryptoNewsUi?,
    showBack: Boolean,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (article == null) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(24.dp)
        ) {
            Text(
                text = "Select a story",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }
        return
    }

    LazyColumn(
        contentPadding = PaddingValues(bottom = 32.dp),
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        if (showBack) {
            item {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp)
                ) {
                    Text(
                        text = "<",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }

        if (article.imageUrl.isNotBlank()) {
            item {
                AsyncImage(
                    model = article.imageUrl,
                    contentDescription = article.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 180.dp, max = 320.dp)
                )
            }
        }

        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 840.dp)
                    .padding(24.dp)
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface
                )
                NewsArticleMeta(article)
                if (article.categories.isNotEmpty()) {
                    NewsCategoryRow(article.categories)
                }
                SelectionContainer {
                    Text(
                        text = article.body.ifBlank { article.excerpt },
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
                ArticleLinkButton(article)
            }
        }
    }
}

@Composable
private fun NewsArticleMeta(article: CryptoNewsUi) {
    val metadata = listOf(article.source, article.publishedOn)
        .filter { it.isNotBlank() }
        .joinToString(" - ")
    val authors = article.authors.takeIf { it.isNotBlank() }

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        if (metadata.isNotBlank()) {
            Text(
                text = metadata,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }
        if (authors != null) {
            Text(
                text = authors,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
private fun NewsCategoryRow(categories: List<String>) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
        categories.forEach { category ->
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ) {
                Box(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
                    Text(
                        text = category,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun ArticleLinkButton(article: CryptoNewsUi) {
    if (article.url.isBlank()) return

    val uriHandler = LocalUriHandler.current
    OutlinedButton(onClick = { uriHandler.openUri(article.url) }) {
        Text("Read full article")
    }
}

@Composable
private fun NewsErrorView(
    error: Throwable,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = error.displayMessage(),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
            OutlinedButton(onClick = onRetryClick) {
                Text("Try again")
            }
        }
    }
}

@Composable
private fun NewsErrorBanner(
    error: Throwable,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = error.displayMessage(),
                color = MaterialTheme.colorScheme.onErrorContainer,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = onRetryClick) {
                Text("Retry")
            }
        }
    }
}

@Composable
private fun Throwable.displayMessage(): String {
    return when (this) {
        is DomainError -> toUiText().asString()
        else -> message ?: "Something went wrong"
    }
}

@Preview
@Composable
private fun CryptoNewsScreenPreview() {
    MaterialTheme {
        CryptoNewsScreen(
            state = CryptoNewsUiState(
                pagedArticles = flowOf(
                    PagingData.from(
                        List(5) { previewNewsArticle.copy(id = "preview-$it") }
                    )
                ),
                selectedArticle = previewNewsArticle
            ),
            onArticleClick = {},
            onBackClick = {}
        )
    }
}
