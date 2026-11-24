package org.example.project.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.example.project.domain.model.ClaimValue
import org.example.project.domain.repository.LoadException
import org.example.project.domain.repository.WikidataRepository
import org.example.project.ui.model.toDetailUi

class EntityDetailViewModel(
    private val entityId: String,
    private val repository: WikidataRepository,
    private val onOpenEntity: (String) -> Unit,
) : ViewModel() {
    private val _state = MutableStateFlow<EntityDetailState>(EntityDetailState.Loading)
    val state: StateFlow<EntityDetailState> = _state.asStateFlow()
    private var request: Job? = null

    init { load() }

    fun onIntent(intent: EntityDetailIntent) {
        when (intent) {
            is EntityDetailIntent.RelatedEntityClicked -> onOpenEntity(intent.id)
            EntityDetailIntent.Retry -> if (_state.value !is EntityDetailState.Loading) load()
        }
    }

    private fun load() {
        request?.cancel()
        _state.value = EntityDetailState.Loading
        request = viewModelScope.launch {
            try {
                val entity = repository.getEntityById(entityId)
                if (entity == null) {
                    _state.value = EntityDetailState.NotFound(entityId)
                    return@launch
                }
                val references = withContext(Dispatchers.Default) {
                    entity.claims.values.flatten().filterIsInstance<ClaimValue.Item>().map { it.id }
                }
                val propertyIds = entity.claims.keys.filterNot(::hasLocalPropertyLabel)
                val propertyLabels = async {
                    try {
                        if (propertyIds.isEmpty()) emptyMap() else repository.getLabels(propertyIds)
                    } catch (_: LoadException) {
                        emptyMap()
                    }
                }
                val labels = repository.getLabels(references) + propertyLabels.await()
                val ui = withContext(Dispatchers.Default) { entity.toDetailUi(labels) }
                ensureActive()
                _state.value = EntityDetailState.Content(ui)
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (failure: LoadException) {
                _state.value = EntityDetailState.Error(failure.failure)
            }
        }
    }
}
