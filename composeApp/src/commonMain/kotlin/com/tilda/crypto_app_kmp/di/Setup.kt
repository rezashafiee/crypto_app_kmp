package com.tilda.crypto_app_kmp.di

import com.tilda.core.data.di.databaseModule
import com.tilda.core.data.di.networkModule
import com.tilda.crypto.data.di.cryptoDataModule
import com.tilda.crypto.presentation.di.cryptoPresentationModule
import com.tilda.feature.news.data.di.newsDataModule
import com.tilda.feature.news.presentation.di.newsPresentationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(app: KoinAppDeclaration? = null) {
    startKoin {
        app?.invoke(this)
        modules(
            appModule,
            networkModule,
            databaseModule,
            cryptoPresentationModule,
            cryptoDataModule,
            newsPresentationModule,
            newsDataModule
        )
    }
}
