package org.example.project.domain.model

data class WikidataEntity(
    val id: String,
    val label: String,
    val description: String,
    val imageUrl: String?,
    val instanceOf: String?,
    val detailedText: String? = null,
    val trivia: String? = null
)
