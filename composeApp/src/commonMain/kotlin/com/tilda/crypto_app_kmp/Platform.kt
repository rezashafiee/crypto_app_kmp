package com.tilda.crypto_app_kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform