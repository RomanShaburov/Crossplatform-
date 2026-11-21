package org.example.project.ui.model

import org.example.project.domain.model.ClaimValue
import org.example.project.domain.model.WikidataEntity

private const val INSTANCE_OF = "P31"

fun WikidataEntity.toCardUi(labels: Map<String, String>): EntityCardUi {
    val type = claims[INSTANCE_OF]?.firstOrNull() as? ClaimValue.Item
    return EntityCardUi(
        id = id,
        label = label,
        description = description,
        typeLabel = type?.let { labels[it.id] ?: it.id },
    )
}

fun WikidataEntity.toDetailUi(labels: Map<String, String>): EntityDetailUi =
    EntityDetailUi(
        id = id,
        label = label,
        description = description,
        aliases = aliases,
        claims = claims.map { (propertyId, values) ->
            ClaimUi(propertyId, values.map { it.toUi(labels) })
        },
        enwikiTitle = enwikiTitle,
    )

private fun ClaimValue.toUi(labels: Map<String, String>): ClaimValueUi = when (this) {
    is ClaimValue.Item -> ClaimValueUi.Link(id, labels[id] ?: id)
    is ClaimValue.Time -> ClaimValueUi.Plain(formatTime(time, precision))
    is ClaimValue.Quantity -> ClaimValueUi.Plain(formatAmount(amount))
    is ClaimValue.Text -> ClaimValueUi.Plain(value)
}

private fun formatTime(time: String, precision: Int): String {
    val date = time.removePrefix("+").substringBefore('T')
    val year = date.removePrefix("-").substringBefore('-').let {
        if (date.startsWith('-')) "-$it" else it
    }
    return when {
        precision >= 11 -> date
        precision == 10 -> date.substringBeforeLast('-')
        else -> year
    }
}

private fun formatAmount(amount: String): String {
    val number = amount.removePrefix("+")
    val sign = if (number.startsWith('-')) "-" else ""
    val parts = number.removePrefix("-").split('.', limit = 2)
    val integer = parts[0].reversed().chunked(3).joinToString(" ").reversed()
    return sign + integer + if (parts.size == 2) ".${parts[1]}" else ""
}
