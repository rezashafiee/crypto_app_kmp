package com.tilda.feature.crypto.domain.interactor


import com.tilda.core.domain.NetworkError
import com.tilda.core.domain.Result
import com.tilda.feature.crypto.domain.model.CoinPrice
import com.tilda.feature.crypto.domain.repository.CoinRepository
import kotlin.time.Instant

class GetCoinHistoryUseCase(
    private val coinRepository: CoinRepository
) {
    suspend operator fun invoke(
        coinSymbol: String,
        end: Instant
    ): Result<List<CoinPrice>, NetworkError> {
        return coinRepository.getCoinsHistory(coinSymbol, end)
    }
}