package org.example.project.ui.detail

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import wikidataentities.shared.generated.resources.*

/**
 * Подписи свойств Wikidata. Идентификатор свойства (`P31`) приходит из API,
 * а подпись к нему — часть интерфейса, поэтому она в ресурсах и переводится.
 */
private val propertyNames: Map<String, StringResource> = mapOf(
    "P31" to Res.string.property_instance_of,
    "P279" to Res.string.property_subclass_of,
    "P17" to Res.string.property_country,
    "P27" to Res.string.property_citizenship,
    "P19" to Res.string.property_place_of_birth,
    "P569" to Res.string.property_date_of_birth,
    "P570" to Res.string.property_date_of_death,
    "P106" to Res.string.property_occupation,
    "P800" to Res.string.property_notable_work,
    "P170" to Res.string.property_creator,
    "P276" to Res.string.property_location,
    "P131" to Res.string.property_admin_unit,
    "P36" to Res.string.property_capital,
    "P1082" to Res.string.property_population,
    "P571" to Res.string.property_inception,
    "P361" to Res.string.property_part_of,
    "P397" to Res.string.property_parent_body,
    "P61" to Res.string.property_inventor,
    "P856" to Res.string.property_website,
)

/** Подпись свойства на языке интерфейса; для незнакомого свойства — его идентификатор. */
@Composable
fun propertyLabel(propertyId: String): String =
    propertyNames[propertyId]?.let { stringResource(it) } ?: propertyId
