package com.tilda.core.data.network

import com.tilda.core.data.BuildKonfig


fun constructUrl(url: String): String {
    return when {
        url.contains(BuildKonfig.BASE_URL) -> url
        url.startsWith("/") -> BuildKonfig.BASE_URL + url.drop(1)
        else -> BuildKonfig.BASE_URL + url
    }
}