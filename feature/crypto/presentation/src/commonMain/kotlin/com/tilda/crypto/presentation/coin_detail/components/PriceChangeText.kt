package com.tilda.crypto.presentation.coin_detail.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.tilda.crypto.presentation.models.DisplayableNumber

private val greenLight = Color(0xFF129423)
private val greenDark = Color(0xFF79DC7A)

@Composable
internal fun PriceChangeText(
    priceChange: DisplayableNumber,
    priceChangePercentage24h: DisplayableNumber
) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.outline
                )
            ) {
                append("Last 24 hrs")
            }
            append(" ")
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Black,
                    fontSize = 12.sp,
                    color = if (priceChange.value > 0) {
                        if (isSystemInDarkTheme()) greenDark else greenLight
                    } else {
                        MaterialTheme.colorScheme.error
                    },
                )
            ) {
                val positiveSign = if (priceChange.value > 0) "+" else ""
                append("$positiveSign${priceChange.formatted} ($positiveSign${priceChangePercentage24h.formatted}%)")
            }
        }
    )
}

@Preview
@Composable
private fun PriceChangeTextPreview() {
    MaterialTheme {
        PriceChangeText(
            priceChange = DisplayableNumber(
                value = 10000.0,
                formatted = "10,000.00"
            ),
            priceChangePercentage24h = DisplayableNumber(
                value = 10.0,
                formatted = "10.00"
            )
        )
    }
}
