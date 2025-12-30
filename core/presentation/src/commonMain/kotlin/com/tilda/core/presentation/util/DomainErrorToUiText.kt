package com.tilda.core.presentation.util


import com.tilda.core.domain.DomainError
import com.tilda.core.domain.NetworkError
import crypto_app_kmp.core.presentation.generated.resources.Res
import crypto_app_kmp.core.presentation.generated.resources.no_internet_error
import crypto_app_kmp.core.presentation.generated.resources.serialization_error
import crypto_app_kmp.core.presentation.generated.resources.server_error
import crypto_app_kmp.core.presentation.generated.resources.timout_error
import crypto_app_kmp.core.presentation.generated.resources.too_many_requests_error
import crypto_app_kmp.core.presentation.generated.resources.unexpected_error
import crypto_app_kmp.core.presentation.generated.resources.unknown_error

fun DomainError.toUiText(): UiText =
    UiText.Resource(when (this) {
        is NetworkError.NoInternetError -> Res.string.no_internet_error
        is NetworkError.ServerError -> Res.string.server_error
        is NetworkError.TimeoutError -> Res.string.timout_error
        is NetworkError.SerializationError -> Res.string.serialization_error
        is NetworkError.TooManyRequestsError -> Res.string.too_many_requests_error
        is NetworkError.UnknownError -> Res.string.unknown_error
        else -> Res.string.unexpected_error
    })