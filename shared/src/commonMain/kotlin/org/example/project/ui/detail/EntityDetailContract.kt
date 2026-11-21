package org.example.project.ui.detail

import androidx.compose.runtime.Immutable
import org.example.project.ui.model.EntityDetailUi
import org.example.project.domain.repository.LoadFailure

@Immutable
sealed interface EntityDetailState {
    data object Loading : EntityDetailState
    data class Error(val failure: LoadFailure) : EntityDetailState
    data class Content(val entity: EntityDetailUi) : EntityDetailState
    data class NotFound(val entityId: String) : EntityDetailState
}

sealed interface EntityDetailIntent {
    data object Retry : EntityDetailIntent
    data class RelatedEntityClicked(val id: String) : EntityDetailIntent
}
