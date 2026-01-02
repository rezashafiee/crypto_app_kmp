package com.tilda.feature.crypto.domain.interactor

import androidx.paging.PagingData
import com.tilda.feature.crypto.domain.model.Coin
import com.tilda.feature.crypto.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow

class GetPagedCoinsUseCase(
    private val repository: CoinRepository
) {
    operator fun invoke(): Flow<PagingData<Coin>> {
        return repository.getPagedCoins()
    }
}