package com.tilda.core.data.di

import com.tilda.core.data.network.HttpClientFactory
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.dsl.module


expect val platformNetworkModule: Module

val networkModule = module {
    includes(platformNetworkModule)

    single<HttpClient> { HttpClientFactory.create(engine = get()) }
}