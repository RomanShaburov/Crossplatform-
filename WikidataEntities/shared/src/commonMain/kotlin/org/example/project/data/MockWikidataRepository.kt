package org.example.project.data

import org.example.project.domain.model.WikidataEntity
import org.example.project.domain.repository.WikidataRepository

class MockWikidataRepository : WikidataRepository {
    private val byId = mockEntities.associateBy { it.id }

    override fun getEntities(): List<WikidataEntity> = mockEntities

    override fun getEntityById(id: String): WikidataEntity? = byId[id]
}
