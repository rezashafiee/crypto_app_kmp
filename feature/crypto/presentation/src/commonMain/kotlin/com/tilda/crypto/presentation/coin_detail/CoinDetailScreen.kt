package com.tilda.crypto.presentation.coin_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tilda.crypto.presentation.coin_detail.components.CompactView
import com.tilda.crypto.presentation.coin_detail.components.TabletView
import com.tilda.crypto.presentation.coin_detail.components.previewLineChartHistory
import com.tilda.crypto.presentation.coin_list.CoinListUiState
import com.tilda.crypto.presentation.models.CoinUi
import com.tilda.crypto.presentation.models.previewCoin

@Composable
fun CoinDetailScreen(
    state: CoinListUiState,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = true,
    onBackClick: () -> Unit = {},
    onFavoriteClick: (CoinUi) -> Unit = {}
) {
    val coin = state.selectedCoin
    if (coin == null) {
        CoinDetailPlaceholder(modifier = modifier)
        return
    }

    BoxWithConstraints(modifier = modifier) {
        if (maxWidth < 600.dp) {
            CompactView(
                coinUi = coin,
                showBackButton = showBackButton,
                onBackClick = onBackClick,
                onFavoriteClick = { onFavoriteClick(coin) }
            )
        } else {
            TabletView(
                coinUi = coin,
                showBackButton = showBackButton,
                onBackClick = onBackClick,
                onFavoriteClick = { onFavoriteClick(coin) }
            )
        }
    }
}

@Composable
private fun CoinDetailPlaceholder(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(24.dp)
    ) {
        Text(
            text = "Select a coin",
            color = MaterialTheme.colorScheme.outline,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview
@Composable
private fun CoinDetailScreenPreview() {
    MaterialTheme {
        CoinDetailScreen(
            state = CoinListUiState(
                selectedCoin = previewCoin.copy(
                    coinPriceHistory = previewLineChartHistory
                )
            )
        )
    }
}
