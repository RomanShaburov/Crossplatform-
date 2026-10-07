package org.example.project.ui.detail

import androidx.compose.runtime.Immutable
import org.example.project.ui.model.EntityDetailUi

/**
 * Состояние экрана детали: сущность нашлась — или её нет в каталоге.
 *
 * Намерений у этого экрана нет: единственное действие на нём — «назад»,
 * и оно живёт в общей шапке приложения.
 */
@Immutable
sealed interface EntityDetailState {
    data class Content(val entity: EntityDetailUi) : EntityDetailState
    data class NotFound(val entityId: String) : EntityDetailState
}
