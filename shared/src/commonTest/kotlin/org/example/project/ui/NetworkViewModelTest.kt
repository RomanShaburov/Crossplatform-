package org.example.project.ui

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.example.project.domain.model.WikidataEntity
import org.example.project.domain.repository.*
import org.example.project.ui.detail.*
import org.example.project.ui.list.*
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class NetworkViewModelTest {
    @BeforeTest fun setup() { Dispatchers.setMain(StandardTestDispatcher()) }
    @AfterTest fun cleanup() { Dispatchers.resetMain() }

    @Test fun debounceCancelsPreviousRequestWithoutError() = runTest {
        val calls = mutableListOf<String>()
        var cancelled = false
        val repository = object : FakeRepository() {
            override suspend fun getEntities(query: String, offset: Int): EntityPage {
                calls += query
                if (query == "old") {
                    try { awaitCancellation() } finally { cancelled = true }
                }
                return EntityPage(listOf(entity(query.ifEmpty { "initial" })), null)
            }
        }
        val vm = EntityListViewModel(repository) {}
        vm.state.first { !it.loading }
        vm.onIntent(EntityListIntent.QueryChanged("old"))
        advanceTimeBy(401)
        runCurrent()
        vm.onIntent(EntityListIntent.QueryChanged("new"))
        vm.onIntent(EntityListIntent.QueryChanged("newer"))
        vm.state.first { !it.loading }
        assertTrue(cancelled)
        assertFalse("new" in calls)
        assertEquals("newer", vm.state.value.items.single().label)
        assertNull(vm.state.value.failure)
    }

    @Test fun paginationKeepsItemsOnFailureAndRetryDeduplicates() = runTest {
        var fail = true
        val repository = object : FakeRepository() {
            override suspend fun getEntities(query: String, offset: Int): EntityPage {
                if (offset == 0) return EntityPage(listOf(entity("one")), 20)
                if (fail) throw LoadException(LoadFailure.NETWORK)
                return EntityPage(listOf(entity("one"), entity("two", "Q2")), null)
            }
        }
        val vm = EntityListViewModel(repository) {}
        vm.state.first { !it.loading }
        vm.onIntent(EntityListIntent.LoadMore)
        vm.state.first { it.failure != null }
        assertEquals(1, vm.state.value.items.size)
        assertEquals(20, vm.state.value.nextOffset)
        fail = false
        vm.onIntent(EntityListIntent.Retry)
        vm.state.first { !it.loading && it.failure == null }
        assertEquals(listOf("Q1", "Q2"), vm.state.value.items.map { it.id })
    }

    @Test fun detailHasLoadingNotFoundAndRetry() = runTest {
        var fail = true
        val repository = object : FakeRepository() {
            override suspend fun getEntityById(id: String): WikidataEntity? {
                if (fail) throw LoadException(LoadFailure.SERVER)
                return null
            }
        }
        val vm = EntityDetailViewModel("Q1", repository) {}
        assertEquals(EntityDetailState.Loading, vm.state.value)
        vm.state.first { it is EntityDetailState.Error }
        fail = false
        vm.onIntent(EntityDetailIntent.Retry)
        assertEquals(EntityDetailState.NotFound("Q1"), vm.state.first { it is EntityDetailState.NotFound })
    }

    private open class FakeRepository : WikidataRepository {
        override suspend fun getEntities(query: String, offset: Int) = EntityPage(emptyList(), null)
        override suspend fun getEntityById(id: String): WikidataEntity? = null
        override suspend fun getLabels(ids: List<String>) = emptyMap<String, String>()
    }
    companion object {
        private fun entity(label: String, id: String = "Q1") = WikidataEntity(id, label, "", emptyList(), emptyMap(), null)
    }
}
