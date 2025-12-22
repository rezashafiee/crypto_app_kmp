package com.tilda.core.domain

sealed class NetworkError : DomainError() {
    data class NoInternetError(val content: String = "") : NetworkError()
    data class TooManyRequestsError(val content: String = "") : NetworkError()
    data class TimeoutError(val content: String = "") : NetworkError()
    data class ServerError(val content: String = "") : NetworkError()
    data class SerializationError(val content: String = "") : NetworkError()
    data class UnknownError(val content: String = "") : NetworkError()
}