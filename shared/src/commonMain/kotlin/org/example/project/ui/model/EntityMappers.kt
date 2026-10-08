package org.example.project.ui.model

import org.example.project.domain.model.ClaimValue
import org.example.project.domain.model.WikidataEntity
import org.example.project.domain.repository.WikidataRepository

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

private fun ClaimValue.toUi(repository: WikidataRepository): ClaimValueUi = when (this) {
    is ClaimValue.Item -> repository.getEntityById(id)
        ?.let { ClaimValueUi.Link(id, it.label) }
        ?: ClaimValueUi.Plain(id)
    is ClaimValue.Time -> ClaimValueUi.Plain(formatTime(time, precision))
    is ClaimValue.Quantity -> ClaimValueUi.Plain(formatAmount(amount))
    is ClaimValue.Text -> ClaimValueUi.Plain(value)
}

private fun formatTime(time: String, precision: Int): String {
    val date = time.removePrefix("+").substringBefore('T')
    return when {
        precision >= 11 -> date
        precision == 10 -> date.substringBeforeLast('-')
        else -> date.substringBefore('-')
    }
}

private fun formatAmount(amount: String): String {
    val digits = amount.removePrefix("+")
    return digits.reversed().chunked(3).joinToString(" ").reversed()
}
