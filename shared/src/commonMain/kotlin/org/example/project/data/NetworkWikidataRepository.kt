package org.example.project.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.*
import org.example.project.data.network.*
import org.example.project.domain.model.ClaimValue
import org.example.project.domain.model.WikidataEntity
import org.example.project.domain.repository.EntityPage
import org.example.project.domain.repository.WikidataRepository

internal class NetworkWikidataRepository(private val api: WikidataApi) : WikidataRepository {
    override suspend fun getEntities(query: String, offset: Int): EntityPage {
        // Blank search keeps the original catalogue's entry point; all content comes from the API.
        if (query.isBlank()) {
            val ids = listOf("Q42", "Q80", "Q937", "Q7186", "Q935", "Q7259", "Q762", "Q12418",
                "Q19675", "Q243", "Q90", "Q142", "Q84", "Q145", "Q64", "Q183", "Q2", "Q544", "Q634", "Q5")
            return EntityPage(load(ids), null)
        }
        val response = api.search(query.trim(), offset)
        val items = load(response.search.map { it.id })
        return EntityPage(items, response.nextOffset)
    }

    override suspend fun getEntityById(id: String): WikidataEntity? {
        if (!id.matches(Regex("Q[1-9][0-9]*"))) return null
        return load(listOf(id)).firstOrNull()
    }

    override suspend fun getLabels(ids: List<String>): Map<String, String> {
        val result = mutableMapOf<String, String>()
        for (batch in ids.distinct().chunked(50)) {
            val response = api.entities(batch, full = false)
            withContext(Dispatchers.Default) {
                response.entities.values.filter { it.missing == null }.forEach {
                    result[it.id] = it.labels["en"]?.value ?: it.id
                }
            }
        }
        return result.toMap()
    }

    private suspend fun load(ids: List<String>): List<WikidataEntity> {
        if (ids.isEmpty()) return emptyList()
        val entities = api.entities(ids).entities
        return withContext(Dispatchers.Default) {
            ids.mapNotNull { id -> entities[id]?.takeIf { it.missing == null }?.toDomain() }
        }
    }
}

internal fun EntityDto.toDomain() = WikidataEntity(
    id = id,
    label = labels["en"]?.value ?: id,
    description = descriptions["en"]?.value.orEmpty(),
    aliases = aliases["en"].orEmpty().map { it.value },
    claims = claims.mapValues { (_, statements) ->
        statements.filter { it.rank != "deprecated" }.mapNotNull { statement ->
            statement.mainsnak.takeIf { it.snaktype == "value" }?.datavalue?.toDomain()
        }
    }.filterValues { it.isNotEmpty() },
    enwikiTitle = sitelinks["enwiki"]?.title,
)

private fun DataValueDto.toDomain(): ClaimValue? {
    val obj = value as? JsonObject
    fun field(key: String) = (obj?.get(key) as? JsonPrimitive)?.contentOrNull
    return when (type) {
        "wikibase-entityid" -> field("id")?.takeIf { it.startsWith("Q") }?.let(ClaimValue::Item)
        "time" -> field("time")?.let { ClaimValue.Time(it, field("precision")?.toIntOrNull() ?: 9) }
        "quantity" -> field("amount")?.let(ClaimValue::Quantity)
        "string" -> (value as? JsonPrimitive)?.contentOrNull?.let(ClaimValue::Text)
        "monolingualtext" -> field("text")?.let(ClaimValue::Text)
        else -> null
    }
}
