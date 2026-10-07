package org.example.project.ui.model

import androidx.compose.runtime.Immutable

/*
 * Модели для экрана: то, что нужно нарисовать, уже в готовом виде.
 *
 * Зачем они, если есть WikidataEntity: экрану не нужно знать формат API
 * («+1952-03-11T00:00:00Z», «Q5»). Перевод из формата API в показываемый вид —
 * работа ViewModel, а composable только рисует.
 *
 * @Immutable обещает компилятору Compose, что объект после создания не меняется.
 * Без этого класс со списком внутри считается нестабильным: List в Kotlin — интерфейс,
 * за которым может прятаться изменяемый список.
 */

/** Карточка в списке. */
@Immutable
data class EntityCardUi(
    val id: String,
    val label: String,
    val description: String,
    /** Подпись «экземпляра» (P31): «human», «sovereign state». Null — если P31 нет. */
    val typeLabel: String?,
)

/** Всё, что показывает экран детали. */
@Immutable
data class EntityDetailUi(
    val id: String,
    val label: String,
    val description: String,
    val aliases: List<String>,
    val claims: List<ClaimUi>,
    val enwikiTitle: String?,
)

/**
 * Одна строка свойств: «Страна: France». Подпись свойства берётся из ресурсов
 * по [propertyId], значения уже готовы к показу.
 */
@Immutable
data class ClaimUi(
    val propertyId: String,
    val values: List<String>,
)
