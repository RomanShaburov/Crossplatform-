package org.example.project.ui.detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.domain.repository.WikidataRepository
import org.example.project.ui.model.toDetailUi

/**
 * Состояние экрана детали одной сущности. На каждую открытую сущность — своя ViewModel
 * (см. ключ в AppNavDisplay), поэтому [entityId] приходит в конструктор.
 */
class EntityDetailViewModel(
    entityId: String,
    repository: WikidataRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(
        repository.getEntityById(entityId)
            ?.let { EntityDetailState.Content(it.toDetailUi(repository)) }
            ?: EntityDetailState.NotFound(entityId),
    )
    val state: StateFlow<EntityDetailState> = _state.asStateFlow()
}
