package com.tilda.crypto.presentation.coin_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.tilda.core.domain.DomainError
import com.tilda.core.presentation.util.toUiText
import com.tilda.crypto.presentation.coin_list.components.CoinListItem
import com.tilda.crypto.presentation.models.CoinUi
import com.tilda.crypto.presentation.models.previewCoin
import kotlinx.coroutines.flow.flowOf

@Composable
fun CoinListScreen(
    uiState: CoinListUiState,
    modifier: Modifier = Modifier,
    onItemClick: (coin: CoinUi) -> Unit = {},
    onFavoriteClick: (coin: CoinUi) -> Unit = {},
    onFavoritesFilterChanged: (showFavoritesOnly: Boolean) -> Unit = {}
) {

    val pagedCoins = uiState.pagedCoins.collectAsLazyPagingItems()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        item {
            CoinListHeader(
                showFavoritesOnly = uiState.showFavoritesOnly,
                onFavoritesFilterChanged = onFavoritesFilterChanged
            )
        }

        if (
            uiState.showFavoritesOnly &&
            pagedCoins.itemCount == 0 &&
            pagedCoins.loadState.refresh is LoadState.NotLoading
        ) {
            item {
                EmptyWatchlistMessage()
            }
        }

        items(pagedCoins.itemCount) { index ->
            pagedCoins[index]?.let {
                CoinListItem(
                    coinUi = it,
                    onFavoriteClick = { onFavoriteClick(it) },
                    modifier = Modifier.clickable(onClick = { onItemClick(it) })
                )
            }
        }

        pagedCoins.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { CircularProgressIndicator() }
                }

                loadState.refresh is LoadState.Error -> {
                    val e = pagedCoins.loadState.refresh as LoadState.Error
                    item {
                        ErrorView(e)
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item { CircularProgressIndicator() }
                }

                loadState.append is LoadState.Error -> {
                    val e = pagedCoins.loadState.append as LoadState.Error
                    item {
                        ErrorView(e)
                    }
                }
            }
        }
    }
}

@Composable
private fun CoinListHeader(
    showFavoritesOnly: Boolean,
    onFavoritesFilterChanged: (showFavoritesOnly: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Coins",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
        SingleChoiceSegmentedButtonRow {
            SegmentedButton(
                selected = !showFavoritesOnly,
                onClick = { onFavoritesFilterChanged(false) },
                shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                icon = {},
                label = { Text("All") }
            )
            SegmentedButton(
                selected = showFavoritesOnly,
                onClick = { onFavoritesFilterChanged(true) },
                shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                icon = {},
                label = { Text("Watchlist") }
            )
        }
    }
}

@Composable
private fun EmptyWatchlistMessage(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "No favorite coins yet",
            color = MaterialTheme.colorScheme.outline,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ErrorView(e: LoadState.Error) {
    Text(
        if (e.error is DomainError)
            (e.error as DomainError).toUiText().asString()
        else
            e.error.toString()
    )
}


@Preview
@Composable
private fun CoinListScreenPreview() {
    MaterialTheme {
        CoinListScreen(
            uiState = CoinListUiState(
                pagedCoins = flowOf(
                    PagingData.from(
                        (1..10).map { previewCoin.copy(id = it) }
                    )
                )
            )
        )
    }
}
