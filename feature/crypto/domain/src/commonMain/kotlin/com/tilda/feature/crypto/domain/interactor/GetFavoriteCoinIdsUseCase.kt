package com.tilda.feature.crypto.domain.interactor

import com.tilda.feature.crypto.domain.repository.CoinRepository

class GetFavoriteCoinIdsUseCase(
    private val repository: CoinRepository
) {
    operator fun invoke() = repository.getFavoriteCoinIds()
}
