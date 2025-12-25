package com.tilda.core.data.network


import com.tilda.core.domain.NetworkError
import com.tilda.core.domain.Result
import io.ktor.client.statement.*
import io.ktor.util.network.*
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, NetworkError> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        return Result.Error(NetworkError.NoInternetError(e.toString()))
    } catch (e: SerializationException) {
        return Result.Error(NetworkError.SerializationError(e.toString()))
    } catch (e: Exception) {
        // if the coroutine would be canceled then this could catch the CancellationException
        // therefor the following line is added
        currentCoroutineContext().ensureActive()
        return Result.Error(NetworkError.UnknownError(e.toString()))
    }

    return responseToResult(response)
}