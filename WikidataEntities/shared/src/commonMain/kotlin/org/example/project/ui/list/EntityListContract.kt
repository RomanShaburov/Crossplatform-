package org.example.project.ui.list

import androidx.compose.runtime.Immutable
import org.example.project.ui.model.EntityCardUi

@Immutable
data class EntityListState(
    val items: List<EntityCardUi> = emptyList(),
)

sealed interface EntityListIntent {
    data class EntityClicked(val id: String) : EntityListIntent
}
