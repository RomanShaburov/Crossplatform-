package org.example.project.data

import org.example.project.domain.model.WikidataEntity
import org.example.project.domain.repository.WikidataRepository

class MockWikidataRepository : WikidataRepository {
    private val byId = mockEntities.associateBy { it.id }

    override fun getEntities(): List<WikidataEntity> = mockEntities

    override fun getEntityById(id: String): WikidataEntity? = byId[id]

    override fun searchEntities(query: String): List<WikidataEntity> {
        val needle = query.trim()
        if (needle.isEmpty()) return mockEntities
        return mockEntities.filter { entity ->
            entity.label.contains(needle, ignoreCase = true) ||
                entity.aliases.any { it.contains(needle, ignoreCase = true) }
        }
    }
}
