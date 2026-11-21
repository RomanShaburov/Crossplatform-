package org.example.project.data.network

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.yield
import kotlinx.serialization.SerializationException
import org.w3c.dom.Worker
import org.w3c.dom.MessageEvent
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal actual suspend fun decodeApiResponse(text: String): ApiResponse {
    val chunks = suspendCancellableCoroutine<List<String>> { continuation ->
        val worker = Worker("network-worker.js")
        worker.onmessage = { event: MessageEvent ->
            worker.terminate()
            val data = event.data
            if (continuation.isActive) {
                if (data == null) continuation.resumeWithException(SerializationException("Invalid API response"))
                else continuation.resume(data.unsafeCast<Array<String>>().toList())
            }
        }
        worker.onerror = {
            worker.terminate()
            if (continuation.isActive) continuation.resumeWithException(SerializationException("Response worker failed"))
            null
        }
        continuation.invokeOnCancellation { worker.terminate() }
        worker.postMessage(text)
    }
    var response = apiJson.decodeFromString<ApiResponse>(chunks.first())
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
