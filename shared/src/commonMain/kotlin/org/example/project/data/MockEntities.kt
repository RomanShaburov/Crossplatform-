package org.example.project.data

import org.example.project.domain.model.ClaimValue
import org.example.project.domain.model.WikidataEntity

val mockEntities: List<WikidataEntity> = listOf(
    WikidataEntity(
        id = "Q42",
        label = "Douglas Adams",
        description = "English writer and humorist (1952–2001)",
        aliases = listOf("Douglas Noël Adams", "Douglas Noel Adams"),
        claims = mapOf(
            "P31" to listOf(item("Q5")),
            "P27" to listOf(item("Q145")),
            "P19" to listOf(item("Q350")),
            "P106" to listOf(item("Q36180"), item("Q28389")),
            "P569" to listOf(day("1952-03-11")),
            "P570" to listOf(day("2001-05-11")),
        ),
        enwikiTitle = "Douglas Adams",
    ),
    WikidataEntity(
        id = "Q80",
        label = "Tim Berners-Lee",
        description = "English computer scientist, inventor of the World Wide Web",
        aliases = listOf("TimBL", "Sir Tim Berners-Lee"),
        claims = mapOf(
            "P31" to listOf(item("Q5")),
            "P27" to listOf(item("Q145")),
            "P19" to listOf(item("Q84")),
            "P106" to listOf(item("Q82594")),
            "P569" to listOf(day("1955-06-08")),
            "P800" to listOf(item("Q466")),
        ),
        enwikiTitle = "Tim Berners-Lee",
    ),
    WikidataEntity(
        id = "Q937",
        label = "Albert Einstein",
        description = "German-born theoretical physicist (1879–1955)",
        aliases = listOf("Einstein"),
        claims = mapOf(
            "P31" to listOf(item("Q5")),
            "P27" to listOf(item("Q183"), item("Q30")),
            "P106" to listOf(item("Q169470")),
            "P569" to listOf(day("1879-03-14")),
            "P570" to listOf(day("1955-04-18")),
        ),
        enwikiTitle = "Albert Einstein",
    ),
    WikidataEntity(
        id = "Q7186",
        label = "Marie Curie",
        description = "Polish and naturalised-French physicist and chemist (1867–1934)",
        aliases = listOf("Maria Skłodowska-Curie", "Marie Skłodowska Curie"),
        claims = mapOf(
            "P31" to listOf(item("Q5")),
            "P27" to listOf(item("Q36"), item("Q142")),
            "P19" to listOf(item("Q270")),
            "P106" to listOf(item("Q169470"), item("Q593644")),
            "P569" to listOf(day("1867-11-07")),
            "P570" to listOf(day("1934-07-04")),
        ),
        enwikiTitle = "Marie Curie",
    ),
    WikidataEntity(
        id = "Q935",
        label = "Isaac Newton",
        description = "English physicist and mathematician (1643–1727)",
        aliases = listOf("Sir Isaac Newton"),
        claims = mapOf(
            "P31" to listOf(item("Q5")),
            "P106" to listOf(item("Q169470"), item("Q170790")),
            "P569" to listOf(day("1643-01-04")),
            "P570" to listOf(day("1727-03-31")),
        ),
        enwikiTitle = "Isaac Newton",
    ),
    WikidataEntity(
        id = "Q7259",
        label = "Ada Lovelace",
        description = "English mathematician, author of the first computer program (1815–1852)",
        aliases = listOf("Augusta Ada King", "Countess of Lovelace"),
        claims = mapOf(
            "P31" to listOf(item("Q5")),
            "P19" to listOf(item("Q84")),
            "P106" to listOf(item("Q170790")),
            "P569" to listOf(day("1815-12-10")),
            "P570" to listOf(day("1852-11-27")),
        ),
        enwikiTitle = "Ada Lovelace",
    ),
    WikidataEntity(
        id = "Q762",
        label = "Leonardo da Vinci",
        description = "Italian Renaissance polymath (1452–1519)",
        aliases = listOf("Leonardo"),
        claims = mapOf(
            "P31" to listOf(item("Q5")),
            "P106" to listOf(item("Q1028181")),
            "P569" to listOf(day("1452-04-15")),
            "P570" to listOf(day("1519-05-02")),
            "P800" to listOf(item("Q12418")),
        ),
        enwikiTitle = "Leonardo da Vinci",
    ),
    WikidataEntity(
        id = "Q12418",
        label = "Mona Lisa",
        description = "portrait painting by Leonardo da Vinci",
        aliases = listOf("La Gioconda", "La Joconde"),
        claims = mapOf(
            "P31" to listOf(item("Q3305213")),
            "P170" to listOf(item("Q762")),
            "P276" to listOf(item("Q19675")),
            "P571" to listOf(year(1503)),
        ),
        enwikiTitle = "Mona Lisa",
    ),
    WikidataEntity(
        id = "Q19675",
        label = "Louvre",
        description = "art museum in Paris, France",
        aliases = listOf("Musée du Louvre", "Louvre Museum"),
        claims = mapOf(
            "P31" to listOf(item("Q33506")),
            "P17" to listOf(item("Q142")),
            "P131" to listOf(item("Q90")),
            "P571" to listOf(year(1793)),
        ),
        enwikiTitle = "Louvre",
    ),
    WikidataEntity(
        id = "Q243",
        label = "Eiffel Tower",
        description = "tower in Paris, France",
        aliases = listOf("Tour Eiffel"),
        claims = mapOf(
            "P31" to listOf(item("Q12518")),
            "P17" to listOf(item("Q142")),
            "P131" to listOf(item("Q90")),
            "P571" to listOf(year(1889)),
        ),
        enwikiTitle = "Eiffel Tower",
    ),
    WikidataEntity(
        id = "Q90",
        label = "Paris",
        description = "capital city of France",
        aliases = listOf("City of Light"),
        claims = mapOf(
            "P31" to listOf(item("Q5119")),
            "P17" to listOf(item("Q142")),
            "P1082" to listOf(amount(2_102_650)),
        ),
        enwikiTitle = "Paris",
    ),
    WikidataEntity(
        id = "Q142",
        label = "France",
        description = "country in Western Europe",
        aliases = listOf("French Republic"),
        claims = mapOf(
            "P31" to listOf(item("Q3624078")),
            "P36" to listOf(item("Q90")),
            "P1082" to listOf(amount(68_373_433)),
        ),
        enwikiTitle = "France",
    ),
    WikidataEntity(
        id = "Q84",
        label = "London",
        description = "capital and largest city of the United Kingdom",
        aliases = emptyList(),
        claims = mapOf(
            "P31" to listOf(item("Q5119")),
            "P17" to listOf(item("Q145")),
            "P1082" to listOf(amount(8_799_800)),
        ),
        enwikiTitle = "London",
    ),
    WikidataEntity(
        id = "Q145",
        label = "United Kingdom",
        description = "country in north-western Europe",
        aliases = listOf("UK", "Britain"),
        claims = mapOf(
            "P31" to listOf(item("Q3624078")),
            "P36" to listOf(item("Q84")),
            "P1082" to listOf(amount(67_596_281)),
        ),
        enwikiTitle = "United Kingdom",
    ),
    WikidataEntity(
        id = "Q64",
        label = "Berlin",
        description = "capital and largest city of Germany",
        aliases = emptyList(),
        claims = mapOf(
            "P31" to listOf(item("Q5119")),
            "P17" to listOf(item("Q183")),
            "P1082" to listOf(amount(3_755_251)),
        ),
        enwikiTitle = "Berlin",
    ),
    WikidataEntity(
        id = "Q183",
        label = "Germany",
        description = "country in Central Europe",
        aliases = listOf("Federal Republic of Germany", "Deutschland"),
        claims = mapOf(
            "P31" to listOf(item("Q3624078")),
            "P36" to listOf(item("Q64")),
            "P1082" to listOf(amount(84_358_845)),
        ),
        enwikiTitle = "Germany",
    ),
    WikidataEntity(
        id = "Q2",
        label = "Earth",
        description = "third planet from the Sun",
        aliases = listOf("the world", "Blue Planet"),
        claims = mapOf(
            "P31" to listOf(item("Q634")),
            "P361" to listOf(item("Q544")),
        ),
        enwikiTitle = "Earth",
    ),
    WikidataEntity(
        id = "Q5",
        label = "human",
        description = "species of hominid",
        aliases = listOf("person", "people"),
        claims = mapOf(
            "P31" to listOf(item("Q16521")),
        ),
        enwikiTitle = "Human",
    ),
    WikidataEntity(
        id = "Q3624078",
        label = "sovereign state",
        description = "state that has the highest authority over a territory",
        aliases = listOf("independent state"),
        claims = mapOf(
            "P279" to listOf(item("Q6256")),
        ),
        enwikiTitle = "Sovereign state",
    ),
    WikidataEntity(
        id = "Q5119",
        label = "capital city",
        description = "primary city of a country or other political entity",
        aliases = listOf("capital"),
        claims = mapOf(
            "P279" to listOf(item("Q515")),
        ),
        enwikiTitle = "Capital city",
    ),
)

private fun item(id: String) = ClaimValue.Item(id)

private fun day(date: String) = ClaimValue.Time("+${date}T00:00:00Z", precision = 11)

private fun year(year: Int) = ClaimValue.Time("+$year-01-01T00:00:00Z", precision = 9)

private fun amount(value: Long) = ClaimValue.Quantity("+$value")
