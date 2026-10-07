package org.example.project.ui.list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.domain.repository.WikidataRepository
import org.example.project.ui.model.toCardUi

/**
 * Единственное место, где меняется состояние списка.
 *
 * Наружу отдаётся [StateFlow] только на чтение, изменить состояние можно лишь
 * намерением через [onIntent]. Отсюда однонаправленность: вниз состояние, вверх намерения.
 *
 * @param onOpenEntity переход на деталь. ViewModel не знает, как устроена навигация, —
 * ей дают функцию, которую надо вызвать.
 */
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
