package org.example.project.domain.model

/**
 * Одна сущность Wikidata.
 *
 * Поля повторяют ответ `wbgetentities` (https://www.wikidata.org/w/api.php?action=wbgetentities&ids=Q42),
 * запрошенный с `languages=en`: в В2 сюда встанет сеть без переименований.
 *
 * - [label], [description], [aliases] — в API это словари по языкам, здесь уже выбран английский.
 * - [claims] — утверждения: ключ — идентификатор свойства (`P31` «экземпляр»),
 *   значение — список, потому что у свойства бывает несколько значений.
 * - [enwikiTitle] — `sitelinks.enwiki.title`, название статьи в английской Википедии.
 */
data class WikidataEntity(
    val id: String,
    val label: String,
    val description: String,
    val aliases: List<String>,
    val claims: Map<String, List<ClaimValue>>,
    val enwikiTitle: String?,
)

/**
 * Значение утверждения. В API у него есть тип (`datavalue.type`), и от типа зависит,
 * как его показать: ссылку на другую сущность можно открыть, дату — нет.
 */
sealed interface ClaimValue {
    /** `wikibase-entityid`: ссылка на другую сущность, например `Q5` — «человек». */
    data class Item(val id: String) : ClaimValue

    /**
     * `time`: дата в формате API (`+1952-03-11T00:00:00Z`).
     * [precision] — точность из API: 11 — день, 10 — месяц, 9 — год.
     */
    data class Time(val time: String, val precision: Int) : ClaimValue

    /** `quantity`: число в формате API, со знаком (`+8799800`). */
    data class Quantity(val amount: String) : ClaimValue

    /** `string`: строка — адрес сайта, внешний идентификатор и т. п. */
    data class Text(val value: String) : ClaimValue
}
