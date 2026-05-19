package com.tilda.feature.crypto.domain.interactor

import com.tilda.feature.crypto.domain.repository.CoinRepository

class SetCoinFavoriteUseCase(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(coinId: Int, isFavorite: Boolean) {
        repository.setCoinFavorite(coinId, isFavorite)
    }
}
