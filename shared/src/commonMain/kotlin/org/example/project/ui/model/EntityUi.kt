package org.example.project.ui.model

import androidx.compose.runtime.Immutable

@Immutable
data class EntityCardUi(
    val id: String,
    val label: String,
    val description: String,
    val typeLabel: String?,
)

@Immutable
data class EntityDetailUi(
    val id: String,
    val label: String,
    val description: String,
    val aliases: List<String>,
    val claims: List<ClaimUi>,
    val enwikiTitle: String?,
)

@Immutable
data class ClaimUi(
    val propertyId: String,
    val values: List<ClaimValueUi>,
)

@Immutable
sealed interface ClaimValueUi {
    data class Link(val entityId: String, val label: String) : ClaimValueUi
    data class Plain(val text: String) : ClaimValueUi
}
