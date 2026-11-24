package org.example.project.domain.repository

import org.example.project.domain.model.WikidataEntity

interface WikidataRepository {
    suspend fun getEntities(query: String = "", offset: Int = 0): EntityPage
    suspend fun getEntityById(id: String): WikidataEntity?
    suspend fun getLabels(ids: List<String>): Map<String, String>
}

data class EntityPage(val items: List<WikidataEntity>, val nextOffset: Int?)
