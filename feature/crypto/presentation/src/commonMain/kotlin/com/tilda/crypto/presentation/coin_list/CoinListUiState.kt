package com.tilda.crypto.presentation.coin_list

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.tilda.crypto.presentation.models.CoinUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
data class CoinListUiState(
    val pagedCoins: Flow<PagingData<CoinUi>> = emptyFlow(),
    val selectedCoin: CoinUi? = null
)