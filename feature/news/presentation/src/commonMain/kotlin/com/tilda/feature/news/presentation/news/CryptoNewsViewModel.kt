package com.tilda.feature.news.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.tilda.feature.news.domain.interactor.GetNewsUseCase
import com.tilda.feature.news.presentation.models.CryptoNewsUi
import com.tilda.feature.news.presentation.models.toCryptoNewsUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class CryptoNewsViewModel(
    getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CryptoNewsUiState())
    val state = _state.asStateFlow()

    init {
        val pagedArticles = try {
            getNewsUseCase()
                .flowOn(Dispatchers.Default)
                .map { pagingData ->
                    pagingData.map { article -> article.toCryptoNewsUi() }
                }
                .cachedIn(viewModelScope)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyFlow()
        }

        _state.update { currentState ->
            currentState.copy(pagedArticles = pagedArticles)
        }
    }

    fun onArticleClicked(article: CryptoNewsUi) {
        _state.update { it.copy(selectedArticle = article) }
    }

    fun clearSelectedArticle() {
        _state.update { it.copy(selectedArticle = null) }
    }
}
