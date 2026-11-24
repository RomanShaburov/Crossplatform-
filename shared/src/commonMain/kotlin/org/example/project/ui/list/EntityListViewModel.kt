package org.example.project.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.example.project.domain.model.ClaimValue
import org.example.project.domain.repository.LoadException
import org.example.project.domain.repository.WikidataRepository
import org.example.project.ui.model.toCardUi

class EntityListViewModel(
    private val repository: WikidataRepository,
    private val onOpenEntity: (String) -> Unit,
) : ViewModel() {
    private val _state = MutableStateFlow(EntityListState())
    val state: StateFlow<EntityListState> = _state.asStateFlow()
    private var request: Job? = null

    init { load() }

    fun onIntent(intent: EntityListIntent) {
        when (intent) {
            is EntityListIntent.QueryChanged -> {
                request?.cancel()
                _state.value = EntityListState(query = intent.query)
                load(debounce = true)
            }
            is EntityListIntent.EntityClicked -> onOpenEntity(intent.id)
            EntityListIntent.Retry -> if (!_state.value.loading) load(append = _state.value.items.isNotEmpty())
            EntityListIntent.LoadMore -> if (!_state.value.loading && _state.value.nextOffset != null) load(append = true)
        }
    }

    private fun load(append: Boolean = false, debounce: Boolean = false) {
        request?.cancel()
        val before = _state.value
        val query = before.query
        val offset = if (append) before.nextOffset ?: return else 0
        _state.update { it.copy(loading = true, failure = null) }
        request = viewModelScope.launch {
            try {
                if (debounce) delay(400)
                val page = repository.getEntities(query, offset)
                val typeIds = withContext(Dispatchers.Default) {
                    page.items.flatMap { it.claims["P31"].orEmpty().filterIsInstance<ClaimValue.Item>().map { it.id } }
                }
                val labels = repository.getLabels(typeIds)
                val cards = withContext(Dispatchers.Default) {
                    val incoming = page.items.map { it.toCardUi(labels) }
                    (if (append) before.items + incoming else incoming).distinctBy { it.id }
                }
                ensureActive()
                _state.update { it.copy(items = cards, loading = false, nextOffset = page.nextOffset, failure = null) }
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (failure: LoadException) {
                _state.update { it.copy(loading = false, failure = failure.failure) }
            }
        }
    }
}
