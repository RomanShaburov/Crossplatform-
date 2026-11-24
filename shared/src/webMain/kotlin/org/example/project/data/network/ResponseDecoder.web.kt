package org.example.project.data.network

import kotlinx.coroutines.yield

internal actual suspend fun decodeApiResponse(text: String): ApiResponse {
    val chunks = readWorkerChunks(text)
    val response = apiJson.decodeFromString<ApiResponse>(chunks.first())
    val entities = linkedMapOf<String, EntityDto>()
    for (chunk in chunks.drop(1)) {
        yield()
        apiJson.decodeFromString<ApiResponse>(chunk).entities.forEach { (id, fragment) ->
            val old = entities[id]
            entities[id] = if (old == null) fragment else old.copy(claims = old.claims.toMutableMap().apply {
                fragment.claims.forEach { (property, values) -> put(property, get(property).orEmpty() + values) }
            })
        }
    }
    return response.copy(entities = entities)
}

internal expect suspend fun readWorkerChunks(text: String): List<String>
