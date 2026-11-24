package org.example.project.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest
import org.example.project.data.network.*
import org.example.project.domain.model.ClaimValue
import org.example.project.domain.repository.*
import kotlin.test.*

class WikidataApiTest {
    @Test
    fun unknownFieldsAndClaimKinds() = runTest {
        val client = HttpClient(MockEngine { respond(ENTITY) })
        try {
            val repository = NetworkWikidataRepository(WikidataApi(client, "https://example.test/api"))
            val item = repository.getEntityById("Q42")!!
            assertEquals("Douglas Adams", item.label)
            assertEquals(listOf("DNA"), item.aliases)
            assertEquals(ClaimValue.Item("Q5"), item.claims["P31"]!!.single())
            assertEquals(ClaimValue.Time("+1952-03-11T00:00:00Z", 11), item.claims["P569"]!!.single())
            assertEquals(ClaimValue.Quantity("+42"), item.claims["P1082"]!!.single())
            assertEquals(ClaimValue.Text("hello"), item.claims["P1"]!!.single())
            assertFalse(item.claims.containsKey("P2"))
        } finally { client.close() }
    }

    @Test
    fun missingEntityIsNotNetworkError() = runTest {
        val client = HttpClient(MockEngine { respond("""{"entities":{"Q42":{"id":"Q42","missing":""}}}""") })
        try {
            assertNull(NetworkWikidataRepository(WikidataApi(client, "https://example.test/api")).getEntityById("Q42"))
        } finally { client.close() }
    }

    @Test
    fun cancellationIsRethrown() = runTest {
        val client = HttpClient(MockEngine { throw CancellationException("cancelled") })
        try {
            assertFailsWith<CancellationException> { WikidataApi(client, "https://example.test/api").search("Earth", 0) }
        } finally { client.close() }
    }

    @Test
    fun apiAndMalformedResponsesAreDifferentErrors() = runTest {
        for ((response, expected) in listOf("""{"error":{"code":"maxlag"}}""" to LoadFailure.SERVER, "broken" to LoadFailure.RESPONSE)) {
            val client = HttpClient(MockEngine { respond(response) })
            try {
                val exception = assertFailsWith<LoadException> { WikidataApi(client, "https://example.test/api").search("Earth", 0) }
                assertEquals(expected, exception.failure)
            } finally { client.close() }
        }
    }

    @Test
    fun searchUsesServerContinuationAndCors() = runTest {
        val client = HttpClient(MockEngine { request ->
            assertEquals("wbsearchentities", request.url.parameters["action"])
            assertEquals("40", request.url.parameters["continue"])
            assertEquals("*", request.url.parameters["origin"])
            respond("""{"search":[],"search-continue":60,"extra":true}""")
        })
        try {
            val page = NetworkWikidataRepository(WikidataApi(client, "https://example.test/api")).getEntities("Earth", 40)
            assertTrue(page.items.isEmpty())
            assertEquals(60, page.nextOffset)
        } finally { client.close() }
    }

    companion object {
        private const val ENTITY = """{"entities":{"Q42":{"id":"Q42","labels":{"en":{"value":"Douglas Adams","language":"en"}},"descriptions":{"en":{"value":"writer"}},"aliases":{"en":[{"value":"DNA"}]},"unknown":{"future":true},"claims":{"P31":[{"mainsnak":{"datavalue":{"type":"wikibase-entityid","value":{"id":"Q5"}}}}],"P569":[{"mainsnak":{"datavalue":{"type":"time","value":{"time":"+1952-03-11T00:00:00Z","precision":11}}}}],"P1082":[{"mainsnak":{"datavalue":{"type":"quantity","value":{"amount":"+42"}}}}],"P1":[{"mainsnak":{"datavalue":{"type":"string","value":"hello"}}}],"P2":[{"mainsnak":{"snaktype":"novalue"}},{"rank":"deprecated","mainsnak":{"datavalue":{"type":"string","value":"old"}}}]}}},"success":1}"""
    }
}
