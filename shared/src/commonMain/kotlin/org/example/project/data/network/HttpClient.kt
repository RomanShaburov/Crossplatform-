package org.example.project.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout

internal expect fun createHttpEngine(): HttpClientEngine

internal fun createHttpClient() = HttpClient(createHttpEngine()) {
    expectSuccess = true
    install(HttpTimeout) {
        requestTimeoutMillis = 30_000
        connectTimeoutMillis = 15_000
        socketTimeoutMillis = 30_000
    }
}
