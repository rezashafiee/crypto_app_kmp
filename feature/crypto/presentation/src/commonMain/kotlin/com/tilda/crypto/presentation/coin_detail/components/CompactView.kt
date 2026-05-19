package com.tilda.crypto.presentation.coin_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.tilda.crypto.presentation.components.CoinTitle
import com.tilda.crypto.presentation.models.CoinUi
import com.tilda.crypto.presentation.models.previewCoin

@Composable
internal fun CompactView(
    coinUi: CoinUi,
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showBackButton) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(48.dp)
                ) {
                    Text(
                        text = "<",
                        color = MaterialTheme.colorScheme.outline,
                        fontWeight = FontWeight.Black,
                        fontSize = 22.sp
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(48.dp))
            }
            CoinTitle(
                coinName = coinUi.name,
                coinSymbol = coinUi.symbol
            )
            FavoriteButton(
                isFavorite = coinUi.isFavorite,
                onClick = onFavoriteClick
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "${coinUi.name} Price",
                    color = MaterialTheme.colorScheme.outline,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                )
                Text(
                    text = coinUi.currentPrice.formatted,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Black,
                    fontSize = 20.sp,
                )
                PriceChangeText(
                    priceChange = coinUi.priceChange24h,
                    priceChangePercentage24h = coinUi.priceChangePercentage24h
                )
            }
            AsyncImage(
                model = coinUi.logoUrl,
                contentDescription = "${coinUi.name} logo",
                modifier = Modifier.size(56.dp)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        ChartComponent(coin = coinUi)
        Spacer(modifier = Modifier.height(32.dp))
        CoinMarketStatistics(
            coinUi = coinUi,
            modifier = Modifier.widthIn(max = 400.dp)
        )
    }
}

@Composable
internal fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(48.dp)
    ) {
        Text(
            text = if (isFavorite) "★" else "☆",
            color = if (isFavorite) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            },
            fontSize = 24.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@Preview
@Composable
private fun CompactViewPreview() {
    MaterialTheme {
        CompactView(
            coinUi = previewCoin.copy(coinPriceHistory = previewLineChartHistory),
            showBackButton = true,
            onBackClick = {},
            onFavoriteClick = {}
        )
    }
}
