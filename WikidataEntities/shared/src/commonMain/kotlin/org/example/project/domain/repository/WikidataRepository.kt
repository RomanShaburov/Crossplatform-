package org.example.project.domain.repository

import org.example.project.domain.model.WikidataEntity

interface WikidataRepository {
    fun getEntities(): List<WikidataEntity>
    fun getEntityById(id: String): WikidataEntity?
}
