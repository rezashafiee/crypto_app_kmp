package com.tilda.crypto.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun CoinTitle(
    coinName: String,
    coinSymbol: String
) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Black,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            ) {
                append(coinName)
            }
            append(" ")
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Black,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.outline
                )
            ) {
                append("(${coinSymbol})")
            }
        }
    )
}


@Preview
@Composable
private fun CoinTitlePreview() {
    CoinTitle(
        "Bitcoin",
        "BTC"
    )
}