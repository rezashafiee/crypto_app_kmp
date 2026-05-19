package com.tilda.crypto.presentation.coin_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun StatisticsComponent(
    marketCap: String,
    volume24h: String,
    popularity: String,
    modifier: Modifier = Modifier,
    lowestPrice: String = "",
    highestPrice: String = "",
) {
    Column(
        modifier = modifier
    ) {
        LabelComponent("Market Statistics")
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                LabelComponent("Market Cap")
                PriceComponent(marketCap)
                Spacer(modifier = Modifier.height(16.dp))
                LabelComponent("Low")
                PriceComponent(lowestPrice)
            }
            Column(horizontalAlignment = Alignment.Start) {
                LabelComponent("Volume 24h")
                PriceComponent(volume24h)
                Spacer(modifier = Modifier.height(16.dp))
                LabelComponent("High")
                PriceComponent(highestPrice)
            }
            Column(horizontalAlignment = Alignment.Start) {
                LabelComponent("Popularity")
                PriceComponent(popularity)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
internal fun LabelComponent(
    text: String,
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.outline,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
    )
}

@Composable
internal fun PriceComponent(
    text: String,
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    )
}

@Preview
@Composable
private fun StatisticsComponentPreview() {
    MaterialTheme {
        StatisticsComponent(
            marketCap = "100.00",
            volume24h = "1,000,000.00",
            popularity = "#1",
            lowestPrice = "95.00",
            highestPrice = "105.00",
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer)
        )
    }
}
