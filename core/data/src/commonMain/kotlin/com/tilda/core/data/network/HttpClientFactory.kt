package com.tilda.core.data.network


import com.tilda.core.data.BuildKonfig
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object HttpClientFactory {

    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            install(Logging) {
                level = if (BuildKonfig.DEBUG_MODE) LogLevel.ALL else LogLevel.NONE
                logger = Logger.DEFAULT
            }
            install(ContentNegotiation) {
                json(
                    json = Json { ignoreUnknownKeys = true }
                )
            }
            defaultRequest {
                contentType(type = ContentType.Application.Json)
            }
        }
    }
}