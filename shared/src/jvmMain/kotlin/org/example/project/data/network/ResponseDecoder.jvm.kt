package org.example.project.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal actual suspend fun decodeApiResponse(text: String): ApiResponse = withContext(Dispatchers.Default) {
    apiJson.decodeFromString<ApiResponse>(text)
}
