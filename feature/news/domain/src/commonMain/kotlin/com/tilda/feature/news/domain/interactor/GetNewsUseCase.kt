package com.tilda.feature.news.domain.interactor

import com.tilda.feature.news.domain.repository.NewsRepository

class GetNewsUseCase(
    private val repository: NewsRepository
) {
    operator fun invoke() = repository.getPagedNews()
}
