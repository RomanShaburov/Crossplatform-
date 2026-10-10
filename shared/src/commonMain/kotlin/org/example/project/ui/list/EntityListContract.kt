package org.example.project.ui.list

import androidx.compose.runtime.Immutable
import org.example.project.ui.model.EntityCardUi

@Immutable
data class EntityListState(
    val query: String = "",
    val items: List<EntityCardUi> = emptyList(),
) {
    val nothingFound: Boolean get() = items.isEmpty() && query.isNotBlank()
}

sealed interface EntityListIntent {
    data class QueryChanged(val query: String) : EntityListIntent
    data class EntityClicked(val id: String) : EntityListIntent
}
