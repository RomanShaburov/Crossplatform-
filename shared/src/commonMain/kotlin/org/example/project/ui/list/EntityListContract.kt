package org.example.project.ui.list

import androidx.compose.runtime.Immutable
import org.example.project.ui.model.EntityCardUi
import org.example.project.domain.repository.LoadFailure

@Immutable
data class EntityListState(
    val query: String = "",
    val items: List<EntityCardUi> = emptyList(),
    val loading: Boolean = true,
    val failure: LoadFailure? = null,
    val nextOffset: Int? = null,
) {
    val nothingFound: Boolean get() = !loading && failure == null && items.isEmpty() && query.isNotBlank()
}

sealed interface EntityListIntent {
    data class QueryChanged(val query: String) : EntityListIntent
    data class EntityClicked(val id: String) : EntityListIntent
    data object Retry : EntityListIntent
    data object LoadMore : EntityListIntent
}
