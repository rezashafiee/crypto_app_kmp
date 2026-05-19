package com.tilda.feature.news.presentation.di

import com.tilda.feature.news.presentation.news.CryptoNewsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val newsPresentationModule = module {
    viewModelOf(::CryptoNewsViewModel)
}
