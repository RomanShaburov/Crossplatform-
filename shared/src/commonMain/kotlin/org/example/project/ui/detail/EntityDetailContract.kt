package org.example.project.ui.detail

import androidx.compose.runtime.Immutable
import org.example.project.ui.model.EntityDetailUi

@Immutable
sealed interface EntityDetailState {
    data class Content(val entity: EntityDetailUi) : EntityDetailState
    data class NotFound(val entityId: String) : EntityDetailState
}
