package com.tilda.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.tilda.core.domain.onError
import com.tilda.core.domain.onSuccess
import com.tilda.feature.crypto.domain.interactor.GetCoinHistoryUseCase
import com.tilda.feature.crypto.domain.interactor.GetPagedCoinsUseCase
import com.tilda.feature.crypto.presentation.models.CoinUi
import com.tilda.feature.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock

class CoinListViewModel(
    getPagedCoinsUseCase: GetPagedCoinsUseCase,
    private val getCoinHistoryUseCase: GetCoinHistoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CoinListUiState())
    val state = _state.asStateFlow()

    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

    init {
        val pagedCoins =
            try {
                getPagedCoinsUseCase()
                    .map { pagingData -> pagingData.map { domainCoin -> domainCoin.toCoinUi() } }
                    .cachedIn(viewModelScope)
            } catch (e: Exception) {
                e.printStackTrace()
                emptyFlow()
            }

        _state.update { currentState ->
            currentState.copy(
                pagedCoins = pagedCoins
            )
        }
    }

    fun onCoinClicked(coinUi: CoinUi) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    selectedCoin = coinUi
                )
            }
            getCoinHistoryUseCase(
                coinSymbol = coinUi.symbol,
                end = Clock.System.now()
            )
                .onSuccess { history ->
                    _state.update {
                        it.copy(
                            selectedCoin = it.selectedCoin?.copy(
                                coinPriceHistory = history
                            )
                        )
                    }
                }
                .onError { error ->
                    _events.send(CoinListEvent.LoadCoinsError(error))
                }
        }
    }
}