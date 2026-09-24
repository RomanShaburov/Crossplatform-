package org.example.project.data.repository

import org.example.project.domain.model.WikidataEntity
import org.example.project.domain.repository.WikidataRepository

class MockWikidataRepository : WikidataRepository {
    private val entities = listOf(
        WikidataEntity("Q42", "Douglas Adams", "English author and humorist", null, "human"),
        WikidataEntity("Q76", "Barack Obama", "44th President of the United States", null, "human"),
        WikidataEntity("Q80", "Tim Berners-Lee", "English computer scientist, inventor of the World Wide Web", null, "human"),
        WikidataEntity("Q937", "Albert Einstein", "German-born theoretical physicist", null, "human"),
        WikidataEntity("Q2", "Earth", "Third planet from the Sun", null, "inner planet"),
        WikidataEntity("Q1", "Universe", "All of space and time and their contents", null, "item"),
        WikidataEntity("Q30", "United States of America", "Country in North America", null, "sovereign state"),
        WikidataEntity("Q142", "France", "Country in Europe", null, "sovereign state"),
        WikidataEntity("Q183", "Germany", "Country in Central Europe", null, "sovereign state"),
        WikidataEntity("Q252", "Indonesia", "Country in Southeast Asia", null, "sovereign state"),
        WikidataEntity("Q668", "India", "Country in South Asia", null, "sovereign state"),
        WikidataEntity("Q148", "China", "Country in East Asia", null, "sovereign state"),
        WikidataEntity("Q159", "Russia", "Country in Eurasia", null, "sovereign state"),
        WikidataEntity("Q258", "South Africa", "Country in Africa", null, "sovereign state"),
        WikidataEntity("Q408", "Australia", "Country in Oceania", null, "sovereign state"),
        WikidataEntity("Q414", "Argentina", "Country in South America", null, "sovereign state"),
        WikidataEntity("Q717", "Venezuela", "Country in South America", null, "sovereign state"),
        WikidataEntity("Q733", "Paraguay", "Country in South America", null, "sovereign state"),
        WikidataEntity("Q739", "Colombia", "Country in South America", null, "sovereign state"),
        WikidataEntity("Q750", "Bolivia", "Country in South America", null, "sovereign state")
    )

    override fun getEntities(): List<WikidataEntity> = entities

    override fun getEntityById(id: String): WikidataEntity? = entities.find { it.id == id }
}
