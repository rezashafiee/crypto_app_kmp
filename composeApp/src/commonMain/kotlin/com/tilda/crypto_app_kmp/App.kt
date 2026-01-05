package com.tilda.crypto_app_kmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tilda.crypto.presentation.coin_list.CoinListScreen
import com.tilda.crypto.presentation.coin_list.CoinListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val viewModel = koinViewModel<CoinListViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        CoinListScreen(state)
    }
}