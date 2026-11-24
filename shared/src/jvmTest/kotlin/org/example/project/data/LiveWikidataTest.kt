package org.example.project.data

import kotlinx.coroutines.runBlocking
import org.example.project.data.network.*
import kotlin.test.*

class LiveWikidataTest {
    @Test
    fun realEngineParsesDetailSearchAndLabels() = runBlocking {
        if (System.getProperty("wikidata.live") != "true") return@runBlocking
        val client = createHttpClient()
        try {
            val repository = NetworkWikidataRepository(WikidataApi(client, BuildKonfig.API_URL))
            val entity = repository.getEntityById("Q42")!!
            assertEquals("Douglas Adams", entity.label)
            assertTrue(entity.claims.isNotEmpty())
            assertEquals("human", repository.getLabels(listOf("Q5"))["Q5"])
            assertEquals("lowest point", repository.getLabels(listOf("P1589"))["P1589"])
            val first = repository.getEntities("Earth", 0)
            assertTrue(first.items.isNotEmpty())
            assertTrue(first.items.any { it.id == "Q2" })
            assertNotNull(first.nextOffset)
            val second = repository.getEntities("Earth", first.nextOffset!!)
            assertTrue(second.items.isNotEmpty())
            assertNotEquals(first.items.map { it.id }, second.items.map { it.id })
            println("Live Wikidata: detail, labels and two search pages OK")
        } finally { client.close() }
    }
}
