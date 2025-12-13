package com.tilda.crypto_app_kmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "crypto_app_kmp",
    ) {
        App()
    }
}