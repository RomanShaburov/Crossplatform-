package org.example.project.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.client.plugins.ResponseException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.example.project.domain.repository.LoadException
import org.example.project.domain.repository.LoadFailure

internal val apiJson = Json { ignoreUnknownKeys = true }

internal class WikidataApi(
    private val client: HttpClient,
    private val endpoint: String,
) {
    suspend fun search(query: String, offset: Int): ApiResponse = request(
        "action" to "wbsearchentities", "search" to query,
        "language" to "en", "uselang" to "en", "type" to "item",
        "limit" to "20", "continue" to offset.toString(),
    )

    suspend fun entities(ids: List<String>, full: Boolean = true): ApiResponse = request(
        "action" to "wbgetentities", "ids" to ids.joinToString("|"),
        "languages" to "en", "languagefallback" to "1",
        "props" to if (full) "labels|descriptions|aliases|claims|sitelinks" else "labels",
        "sitefilter" to "enwiki",
    )

    private suspend fun request(vararg parameters: Pair<String, String>): ApiResponse {
        try {
            val text = client.get(endpoint) {
                parameter("format", "json")
                parameter("origin", "*") // Wikidata CORS for JS and wasm.
                parameters.forEach { (key, value) -> parameter(key, value) }
            }.bodyAsText()
            return decodeApiResponse(text).also {
                if (it.error != null) throw LoadException(LoadFailure.SERVER)
            }
        } catch (cancelled: CancellationException) {
            throw cancelled
        } catch (failure: LoadException) {
            throw failure
        } catch (failure: SerializationException) {
            throw LoadException(LoadFailure.RESPONSE, failure)
        } catch (failure: ResponseException) {
            throw LoadException(LoadFailure.SERVER, failure)
        } catch (failure: Exception) {
            throw LoadException(LoadFailure.NETWORK, failure)
        }
    }
}
