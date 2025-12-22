package com.tilda.core.data.network


import com.tilda.core.domain.NetworkError
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import com.tilda.core.domain.Result

suspend inline fun <reified T> responseToResult(response: HttpResponse): Result<T, NetworkError> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                Result.Error(NetworkError.SerializationError(e.toString()))
            }
        }

        408 -> Result.Error(NetworkError.TimeoutError(response.body()))
        429 -> Result.Error(NetworkError.TooManyRequestsError(response.body()))
        in 500..599 -> Result.Error(NetworkError.ServerError(response.body()))
        else -> Result.Error(NetworkError.UnknownError(response.body()))
    }
}