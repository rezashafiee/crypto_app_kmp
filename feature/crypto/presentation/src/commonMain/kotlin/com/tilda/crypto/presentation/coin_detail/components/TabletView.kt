package com.tilda.crypto.presentation.coin_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.tilda.crypto.presentation.models.CoinUi
import com.tilda.crypto.presentation.models.previewCoin

@Composable
internal fun TabletView(
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
            .padding(24.dp)
    ) {
        TabletHeader(
            coinUi = coinUi,
            showBackButton = showBackButton,
            onBackClick = onBackClick,
            onFavoriteClick = onFavoriteClick
        )
        Spacer(modifier = Modifier.height(24.dp))
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (maxWidth >= 840.dp) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ChartComponent(
                        coin = coinUi,
                        modifier = Modifier.weight(1.6f)
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        modifier = Modifier
                            .weight(1f)
                            .widthIn(min = 280.dp, max = 420.dp)
                    ) {
                        PriceSummaryCard(coinUi = coinUi)
                        CoinMarketStatistics(coinUi = coinUi)
                    }
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PriceSummaryCard(coinUi = coinUi)
                    ChartComponent(coin = coinUi)
                    CoinMarketStatistics(
                        coinUi = coinUi,
                        modifier = Modifier.widthIn(max = 520.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun TabletHeader(
    coinUi: CoinUi,
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
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
                Spacer(modifier = Modifier.width(8.dp))
            }
            AsyncImage(
                model = coinUi.logoUrl,
                contentDescription = "${coinUi.name} logo",
                modifier = Modifier.size(56.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = coinUi.name,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Black,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "${coinUi.symbol} | Rank #${coinUi.rank}",
                    color = MaterialTheme.colorScheme.outline,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            }
        }
        FavoriteButton(
            isFavorite = coinUi.isFavorite,
            onClick = onFavoriteClick
        )
    }
}

@Composable
private fun PriceSummaryCard(
    coinUi: CoinUi,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "${coinUi.name} Price",
                color = MaterialTheme.colorScheme.outline,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp
            )
            Text(
                text = coinUi.currentPrice.formatted,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Black,
                fontSize = 28.sp
            )
            PriceChangeText(
                priceChange = coinUi.priceChange24h,
                priceChangePercentage24h = coinUi.priceChangePercentage24h
            )
        }
    }
}

@Preview
@Composable
private fun TabletViewPreview() {
    MaterialTheme {
        TabletView(
            coinUi = previewCoin.copy(coinPriceHistory = previewLineChartHistory),
            showBackButton = false,
            onBackClick = {},
            onFavoriteClick = {}
        )
    }
}
