package org.example.project.data

import org.example.project.domain.model.WikidataEntity
import org.example.project.domain.repository.WikidataRepository

class MockWikidataRepository : WikidataRepository {
    private val entities = listOf(
        WikidataEntity(
            id = "Q42",
            label = "Douglas Adams",
            description = "English author and humorist",
            imageUrl = null,
            instanceOf = "human",
            detailedText = "Douglas Noel Adams was an English author, screenwriter, essayist, humorist, satirist and dramatist. Adams was author of The Hitchhiker's Guide to the Galaxy, which originated in 1978 as a BBC radio comedy before developing into a \"trilogy\" of five books.",
            trivia = "The number 42 is, in The Hitchhiker's Guide to the Galaxy, the \"Answer to the Ultimate Question of Life, the Universe, and Everything\"."
        ),
        WikidataEntity(
            id = "Q80",
            label = "Tim Berners-Lee",
            description = "Inventor of the World Wide Web",
            imageUrl = null,
            instanceOf = "human",
            detailedText = "Sir Timothy John Berners-Lee is an English computer scientist best known as the inventor of the World Wide Web. He implemented the first successful communication between an HTTP client and server via the internet in 1989.",
            trivia = "He was knighted by Queen Elizabeth II in 2004 for his pivotal work."
        ),
        WikidataEntity(
            id = "Q937",
            label = "Albert Einstein",
            description = "Theoretical physicist",
            imageUrl = null,
            instanceOf = "human",
            detailedText = "Developed the theory of relativity, one of the two pillars of modern physics. His work is also known for its influence on the philosophy of science.",
            trivia = "Einstein was offered the presidency of Israel in 1952 but turned it down."
        ),
        WikidataEntity(
            id = "Q2",
            label = "Earth",
            description = "Third planet from the Sun",
            imageUrl = null,
            instanceOf = "inner planet",
            detailedText = "Earth is the third planet from the Sun and the only astronomical object known to harbor life. Its atmosphere is rich in nitrogen and oxygen, and it is the only planet not named after a god.",
            trivia = "Earth is about 4.54 billion years old."
        ),
        WikidataEntity(
            id = "Q1",
            label = "Universe",
            description = "All of space and time",
            imageUrl = null,
            instanceOf = "item",
            detailedText = "The Universe encompasses everything from the smallest subatomic particles to the largest galaxy filaments.",
            trivia = "The observable universe is expanding at an accelerating rate."
        ),
        WikidataEntity(
            id = "Q30",
            label = "USA",
            description = "Country in North America",
            imageUrl = null,
            instanceOf = "sovereign state",
            detailedText = "A federal republic consisting of 50 states and a federal district. It is the world's third-largest country by area.",
            trivia = "The current 50-star American flag was designed by a 17-year-old."
        ),
        WikidataEntity(
            id = "Q142",
            label = "France",
            description = "Country in Europe",
            imageUrl = null,
            instanceOf = "sovereign state",
            detailedText = "France is a country located primarily in Western Europe. It is known for its fashion, art, and gastronomy.",
            trivia = "France is the most visited country in the world."
        ),
        WikidataEntity(
            id = "Q183",
            label = "Germany",
            description = "Country in Central Europe",
            imageUrl = null,
            instanceOf = "sovereign state",
            detailedText = "A major economic and political power in Europe. Germany has the largest economy in the European Union.",
            trivia = "Germany has over 1,500 different types of beer."
        ),
        WikidataEntity(
            id = "Q252",
            label = "Indonesia",
            description = "Country in SE Asia",
            imageUrl = null,
            instanceOf = "sovereign state",
            detailedText = "Located between the Indian and Pacific oceans, it is the world's largest island country.",
            trivia = "Indonesia is home to the Komodo dragon."
        ),
        WikidataEntity(
            id = "Q668",
            label = "India",
            description = "Country in South Asia",
            imageUrl = null,
            instanceOf = "sovereign state",
            detailedText = "The most populous country and the largest democracy in the world.",
            trivia = "India was the first country to mine diamonds."
        ),
        WikidataEntity(
            id = "Q148",
            label = "China",
            description = "Country in East Asia",
            imageUrl = null,
            instanceOf = "sovereign state",
            detailedText = "The world's second-most populous country with a history spanning thousands of years.",
            trivia = "Ice cream was invented in China around 200 BC."
        ),
        WikidataEntity(
            id = "Q159",
            label = "Russia",
            description = "Country in Eurasia",
            imageUrl = null,
            instanceOf = "sovereign state",
            detailedText = "The largest country in the world by area, spanning eleven time zones.",
            trivia = "The Trans-Siberian Railway crosses 8 time zones."
        ),
        WikidataEntity(
            id = "Q258",
            label = "South Africa",
            description = "Country in Africa",
            imageUrl = null,
            instanceOf = "sovereign state",
            detailedText = "Known for its diverse culture and history. It has the largest economy in Africa.",
            trivia = "It is the only country with three capital cities."
        ),
        WikidataEntity(
            id = "Q408",
            label = "Australia",
            description = "Country in Oceania",
            imageUrl = null,
            instanceOf = "sovereign state",
            detailedText = "A country and a continent surrounded by the Indian and Pacific oceans.",
            trivia = "Australia has more than 10,000 beaches."
        ),
        WikidataEntity(
            id = "Q414",
            label = "Argentina",
            description = "Country in South America",
            imageUrl = null,
            instanceOf = "sovereign state",
            detailedText = "The largest Spanish-speaking nation by area, known for its diverse geography.",
            trivia = "The name comes from the Latin word for silver."
        ),
        WikidataEntity(
            id = "Q717",
            label = "Venezuela",
            description = "Country in South America",
            imageUrl = null,
            instanceOf = "sovereign state",
            detailedText = "A country on the northern coast of South America with diverse natural attractions.",
            trivia = "It has the world's largest known oil reserves."
        ),
        WikidataEntity(
            id = "Q733",
            label = "Paraguay",
            description = "Country in South America",
            imageUrl = null,
            instanceOf = "sovereign state",
            detailedText = "A landlocked country known as the 'Heart of South America'.",
            trivia = "Paraguay's flag has different emblems on each side."
        ),
        WikidataEntity(
            id = "Q739",
            label = "Colombia",
            description = "Country in South America",
            imageUrl = null,
            instanceOf = "sovereign state",
            detailedText = "Characterized by its diverse geography, including the Andes and Amazon rainforest.",
            trivia = "Colombia is the leading producer of emeralds."
        ),
        WikidataEntity(
            id = "Q750",
            label = "Bolivia",
            description = "Country in South America",
            imageUrl = null,
            instanceOf = "sovereign state",
            detailedText = "A landlocked country with the highest administrative capital in the world, La Paz.",
            trivia = "Bolivia has over 30 official languages."
        ),
        WikidataEntity(
            id = "Q145",
            label = "United Kingdom",
            description = "Country in Europe",
            imageUrl = null,
            instanceOf = "sovereign state",
            detailedText = "An island nation in northwestern Europe made up of England, Scotland, Wales and Northern Ireland.",
            trivia = "The UK is the first country to industrialize."
        )
    )

    override fun getEntities(): List<WikidataEntity> = entities

    override fun getEntityById(id: String): WikidataEntity? = entities.find { it.id == id }
}
