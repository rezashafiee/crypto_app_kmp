package com.tilda.crypto_app_kmp

import androidx.compose.ui.window.ComposeUIViewController
import com.tilda.crypto_app_kmp.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }