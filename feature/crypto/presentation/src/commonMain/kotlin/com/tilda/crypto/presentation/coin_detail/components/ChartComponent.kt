package com.tilda.crypto.presentation.coin_detail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tilda.crypto.presentation.models.CoinUi
import com.tilda.crypto.presentation.models.previewCoin

@Composable
internal fun ChartComponent(
    coin: CoinUi,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = coin.coinPriceHistory.isNotEmpty()
    ) {
        Card(
            modifier = modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            PriceLineChart(
                history = coin.coinPriceHistory
            )
        }
    }
}

@Preview
@Composable
private fun ChartComponentPreview() {
    MaterialTheme {
        ChartComponent(
            previewCoin.copy(
                coinPriceHistory = previewLineChartHistory
            )
        )
    }
}
