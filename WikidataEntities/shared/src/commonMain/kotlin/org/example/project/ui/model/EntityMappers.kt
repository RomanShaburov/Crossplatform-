package org.example.project.ui.model

import org.example.project.domain.model.ClaimValue
import org.example.project.domain.model.WikidataEntity
import org.example.project.domain.repository.WikidataRepository

/** Свойство «экземпляр» — по нему в списке пишется тип сущности. */
private const val INSTANCE_OF = "P31"

fun WikidataEntity.toCardUi(repository: WikidataRepository): EntityCardUi {
    val type = claims[INSTANCE_OF]?.firstOrNull() as? ClaimValue.Item
    return EntityCardUi(
        id = id,
        label = label,
        description = description,
        typeLabel = type?.let { repository.getEntityById(it.id)?.label ?: it.id },
    )
}

fun WikidataEntity.toDetailUi(repository: WikidataRepository): EntityDetailUi =
    EntityDetailUi(
        id = id,
        label = label,
        description = description,
        aliases = aliases,
        claims = claims.map { (propertyId, values) ->
            ClaimUi(propertyId, values.map { it.toUi(repository) })
        },
        enwikiTitle = enwikiTitle,
    )

/** Значение утверждения в виде текста для экрана. */
private fun ClaimValue.toUi(repository: WikidataRepository): String = when (this) {
    // Ссылка на сущность: её подпись, если она есть в каталоге, иначе идентификатор.
    is ClaimValue.Item -> repository.getEntityById(id)?.label ?: id
    is ClaimValue.Time -> formatTime(time, precision)
    is ClaimValue.Quantity -> formatAmount(amount)
    is ClaimValue.Text -> value
}

/** «+1952-03-11T00:00:00Z» → «1952-03-11», «1952-03» или «1952» — по точности из API. */
private fun formatTime(time: String, precision: Int): String {
    val date = time.removePrefix("+").substringBefore('T')
    return when {
        precision >= 11 -> date
        precision == 10 -> date.substringBeforeLast('-')
        else -> date.substringBefore('-')
    }
}

/** «+8799800» → «8 799 800»: разряды через пробел, чтобы число читалось. */
private fun formatAmount(amount: String): String {
    val digits = amount.removePrefix("+")
    return digits.reversed().chunked(3).joinToString(" ").reversed()
}
