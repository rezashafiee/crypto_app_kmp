package com.tilda.crypto.presentation.coin_list

import com.tilda.core.domain.DomainError

sealed interface CoinListEvent {
    data class LoadCoinsError(val error: DomainError) : CoinListEvent
}