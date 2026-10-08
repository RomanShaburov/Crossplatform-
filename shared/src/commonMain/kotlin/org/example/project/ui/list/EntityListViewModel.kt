package org.example.project.ui.list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.domain.repository.WikidataRepository
import org.example.project.ui.model.toCardUi

class EntityListViewModel(
    repository: WikidataRepository,
    private val onOpenEntity: (String) -> Unit,
) : ViewModel() {
    private val _state = MutableStateFlow(
        EntityListState(items = repository.getEntities().map { it.toCardUi(repository) }),
    )
    val state: StateFlow<EntityListState> = _state.asStateFlow()

    fun onIntent(intent: EntityListIntent) {
        when (intent) {
            is EntityListIntent.EntityClicked -> onOpenEntity(intent.id)
        }
    }
}
