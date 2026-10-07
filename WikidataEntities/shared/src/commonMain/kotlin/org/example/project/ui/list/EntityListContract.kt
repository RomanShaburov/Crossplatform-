package org.example.project.ui.list

import androidx.compose.runtime.Immutable
import org.example.project.ui.model.EntityCardUi

/**
 * Состояние экрана списка — всё, что нужно нарисовать, одним объектом.
 * Экран ничего не спрашивает сам: получил состояние — нарисовал.
 */
@Immutable
data class EntityListState(
    val items: List<EntityCardUi> = emptyList(),
)

/**
 * Намерения — всё, что пользователь может сделать на экране.
 * Экран не меняет состояние сам, а сообщает о намерении во ViewModel.
 */
sealed interface EntityListIntent {
    data class EntityClicked(val id: String) : EntityListIntent
}
