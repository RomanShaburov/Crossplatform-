package org.example.project.domain.repository

import org.example.project.domain.model.WikidataEntity

/**
 * Откуда приложение берёт сущности. Экраны знают только этот интерфейс:
 * в В1 за ним моки, в В2 — сеть, и экраны этого не заметят.
 */
interface WikidataRepository {
    fun getEntities(): List<WikidataEntity>
    fun getEntityById(id: String): WikidataEntity?
}
