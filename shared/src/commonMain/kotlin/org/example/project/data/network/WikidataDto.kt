package org.example.project.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
internal data class ApiResponse(
    val entities: Map<String, EntityDto> = emptyMap(),
    val search: List<SearchDto> = emptyList(),
    @SerialName("search-continue") val nextOffset: Int? = null,
    val error: ApiErrorDto? = null,
)

@Serializable
internal data class ApiErrorDto(val code: String = "unknown")

@Serializable
internal data class SearchDto(val id: String)

@Serializable
internal data class LocalizedDto(val value: String = "")

@Serializable
internal data class SiteLinkDto(val title: String = "")

@Serializable
internal data class EntityDto(
    val id: String = "",
    val missing: JsonElement? = null,
    val labels: Map<String, LocalizedDto> = emptyMap(),
    val descriptions: Map<String, LocalizedDto> = emptyMap(),
    val aliases: Map<String, List<LocalizedDto>> = emptyMap(),
    val claims: Map<String, List<StatementDto>> = emptyMap(),
    val sitelinks: Map<String, SiteLinkDto> = emptyMap(),
)

@Serializable
internal data class StatementDto(val mainsnak: SnakDto, val rank: String = "normal")

@Serializable
internal data class SnakDto(val snaktype: String = "value", val datavalue: DataValueDto? = null)

@Serializable
internal data class DataValueDto(val type: String, val value: JsonElement)
