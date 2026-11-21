import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.project.data.network.*

private val json = Json { ignoreUnknownKeys = true }

// Kotlin/JS runs in a browser Worker. Neither parsing a large response nor walking its claims
// blocks the UI. Deliver bounded pieces so the wasm receiver can yield between them.
fun main() {
    val worker: dynamic = js("self")
    worker.onmessage = { event: dynamic ->
        try {
            val response = json.decodeFromString<ApiResponse>(event.data as String)
            val pieces = mutableListOf(json.encodeToString(response.copy(entities = emptyMap())))
            response.entities.forEach { (id, entity) ->
                pieces += json.encodeToString(ApiResponse(entities = mapOf(id to entity.copy(claims = emptyMap()))))
                entity.claims.forEach { (property, values) ->
                    values.chunked(10).forEach { batch ->
                        pieces += json.encodeToString(ApiResponse(entities = mapOf(id to EntityDto(id = entity.id,
                            claims = mapOf(property to batch)))))
                    }
                }
            }
            worker.postMessage(pieces.toTypedArray())
        } catch (_: Exception) {
            worker.postMessage(null)
        }
    }
}
