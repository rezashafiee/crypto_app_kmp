package com.tilda.crypto.presentation.coin_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.tilda.core.domain.util.DomainError
import com.tilda.core.presentation.theme.CryptoTheme
import com.tilda.core.presentation.util.getErrorMessage
import com.tilda.crypto.presentation.coin_list.components.CoinListItem
import com.tilda.feature.crypto.presentation.coin_list.CoinListUiState
import com.tilda.feature.crypto.presentation.models.CoinUi
import com.tilda.feature.crypto.presentation.models.previewCoin
import kotlinx.coroutines.flow.flowOf
import kotlin.collections.get

@Composable
fun CoinListScreen(
    uiState: CoinListUiState,
    modifier: Modifier = Modifier,
    onItemClick: (coin: CoinUi) -> Unit = {}
) {

    val context = LocalContext.current
    val pagedCoins = uiState.pagedCoins.collectAsLazyPagingItems()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        items(pagedCoins.itemCount) { index ->
            pagedCoins[index]?.let {
                CoinListItem(
                    coinUi = it,
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
                        Text(getErrorMessage(context, e.error as DomainError))
                        Text(e.error.toString())
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item { CircularProgressIndicator() }
                }

                loadState.append is LoadState.Error -> {
                    val e = pagedCoins.loadState.append as LoadState.Error
                    item {
                        Text(getErrorMessage(context, e.error as DomainError))
                        Text(e.error.toString())
                    }
                }
            }
        }
    }
}

@PreviewLightDark
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