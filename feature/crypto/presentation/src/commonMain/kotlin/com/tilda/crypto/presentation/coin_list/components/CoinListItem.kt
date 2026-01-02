package com.tilda.crypto.presentation.coin_list.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.tilda.crypto.presentation.components.CoinTitle
import com.tilda.feature.crypto.presentation.models.CoinUi
import com.tilda.feature.crypto.presentation.models.previewCoin

val greenLight = Color(0xFF129423)
val greenDark = Color(0xFF79DC7A)

@Composable
fun CoinListItem(
    coinUi: CoinUi,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Text(
                text = coinUi.rank,
                color = MaterialTheme.colorScheme.outline,
                fontWeight = FontWeight.Black,
                fontSize = 12.sp,
            )
            AsyncImage(
                model = coinUi.logoUrl,
                contentDescription = "${coinUi.name} logo",
                modifier = Modifier.size(48.dp)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            )
            {
                CoinTitle(
                    coinUi.name,
                    coinUi.symbol
                )
                Text(
                    text = "${coinUi.marketCapShorted.formatted} Billions",
                    color = MaterialTheme.colorScheme.outline,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End,
            )
            {
                Text(
                    text = coinUi.currentPrice.formatted,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Black,
                    fontSize = 12.sp,
                )
                Text(
                    text = "${coinUi.priceChange24h.formatted} (${coinUi.priceChangePercentage24h.formatted}%)",
                    color = if (coinUi.priceChange24h.value > 0) {
                        if (isSystemInDarkTheme()) greenDark else greenLight
                    } else {
                        MaterialTheme.colorScheme.error
                    },
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 10.sp,
                )
            }
        }
    }
}


@Preview
@Composable
private fun CoinListItemPreview() {
    MaterialTheme {
        CoinListItem(
            previewCoin
        )
    }
}
