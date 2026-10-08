package org.example.project.ui.detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.domain.repository.WikidataRepository
import org.example.project.ui.model.toDetailUi

class EntityDetailViewModel(
    entityId: String,
    repository: WikidataRepository,
    private val onOpenEntity: (String) -> Unit,
) : ViewModel() {
    private val _state = MutableStateFlow(
        repository.getEntityById(entityId)
            ?.let { EntityDetailState.Content(it.toDetailUi(repository)) }
            ?: EntityDetailState.NotFound(entityId),
    )
    val state: StateFlow<EntityDetailState> = _state.asStateFlow()

    fun onIntent(intent: EntityDetailIntent) {
        when (intent) {
            is EntityDetailIntent.RelatedEntityClicked -> onOpenEntity(intent.id)
        }
    }
}
