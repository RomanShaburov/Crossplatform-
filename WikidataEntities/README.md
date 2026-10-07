# Сущности Wikidata — каркас на Compose Multiplatform (ЛР1)

Каталог сущностей Wikidata: список и деталь, переключение темы и языка интерфейса.
Данные — моки по форме ответа `wbgetentities`, сети в этой вехе нет.

В этой вехе один таргет — **desktop (JVM)**.

## Запуск

```bash
./gradlew :desktopApp:run
```

## Проверки

```bash
python tools/check-strings.py          # ключи строк совпадают в обеих локалях
./gradlew :shared:compileKotlinJvm     # отчёт компилятора Compose появится в
                                       # shared/build/compose_compiler/shared-composables.txt
```

## Устройство

| Путь (`shared/src/commonMain/kotlin/org/example/project/`) | Что это |
|---|---|
| `domain/model/WikidataEntity.kt` | модель сущности, поля как в ответе API |
| `data/MockEntities.kt` | моки — единственное место с предметными данными |
| `ui/list/`, `ui/detail/` | экраны: состояние (State), намерения (Intent), ViewModel, экран |
| `ui/navigation/` | маршруты, стек экранов, NavDisplay с анимациями |
| `ui/locale/` | смена языка интерфейса (реализация — в `jvmMain`) |
| `ui/components/AppScaffold.kt` | общая шапка: назад, заголовок, язык, тема |
| `composeResources/values`, `values-en` | подписи интерфейса, две локали |
