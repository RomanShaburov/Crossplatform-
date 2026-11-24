@file:OptIn(kotlin.js.ExperimentalWasmJsInterop::class)

package org.example.project.data.network

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.SerializationException
import org.w3c.dom.Worker
import org.w3c.dom.MessageEvent
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.js.*

internal actual suspend fun readWorkerChunks(text: String): List<String> = suspendCancellableCoroutine<List<String>> { continuation ->
        val worker = Worker("network-worker.js")
        worker.onmessage = { event: MessageEvent ->
            worker.terminate()
            val data = event.data
            if (continuation.isActive) {
                if (data == null) continuation.resumeWithException(SerializationException("Invalid API response"))
                else continuation.resume(data.unsafeCast<JsArray<JsString>>().let { array -> List(array.length) { array[it].toString() } })
            }
        }
        worker.onerror = {
            worker.terminate()
            if (continuation.isActive) continuation.resumeWithException(SerializationException("Response worker failed"))
            null
        }
        continuation.invokeOnCancellation { worker.terminate() }
        worker.postMessage(text.toJsString())
    }
