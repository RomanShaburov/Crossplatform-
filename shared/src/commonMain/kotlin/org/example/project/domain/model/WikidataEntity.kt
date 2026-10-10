package org.example.project.domain.model

data class WikidataEntity(
    val id: String,
    val label: String,
    val description: String,
    val aliases: List<String>,
    val claims: Map<String, List<ClaimValue>>,
    val enwikiTitle: String?,
)

sealed interface ClaimValue {
    data class Item(val id: String) : ClaimValue

    data class Time(val time: String, val precision: Int) : ClaimValue

    data class Quantity(val amount: String) : ClaimValue

    data class Text(val value: String) : ClaimValue
}
