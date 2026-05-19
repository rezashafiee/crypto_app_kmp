package com.tilda.feature.news.presentation.news

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.tilda.feature.news.presentation.models.CryptoNewsUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
data class CryptoNewsUiState(
    val pagedArticles: Flow<PagingData<CryptoNewsUi>> = emptyFlow(),
    val selectedArticle: CryptoNewsUi? = null
)
