package org.example.project.ui.list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.project.domain.repository.WikidataRepository
import org.example.project.ui.model.toCardUi

class EntityListViewModel(
    private val repository: WikidataRepository,
    private val onOpenEntity: (String) -> Unit,
) : ViewModel() {
    private val _state = MutableStateFlow(
        EntityListState(items = repository.getEntities().map { it.toCardUi(repository) }),
    )
    val state: StateFlow<EntityListState> = _state.asStateFlow()

    fun onIntent(intent: EntityListIntent) {
        when (intent) {
            is EntityListIntent.QueryChanged -> search(intent.query)
            is EntityListIntent.EntityClicked -> onOpenEntity(intent.id)
        }
    }

    private fun search(query: String) {
        val items = repository.searchEntities(query).map { it.toCardUi(repository) }
        _state.update { it.copy(query = query, items = items) }
    }
}
