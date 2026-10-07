package org.example.project.ui.locale

import androidx.compose.runtime.Composable

/**
 * Языки интерфейса. RU — базовая локаль (`values/`), EN — `values-en/`.
 * [tag] — код языка, по которому ресурсы выбирают нужную папку.
 */
enum class AppLanguage(val tag: String) {
    RU("ru"),
    EN("en");

    fun next(): AppLanguage = entries[(ordinal + 1) % entries.size]
}

/**
 * Показывает [content] на языке [language].
 *
 * Ресурсы Compose сами смотрят на язык системы, и «переключателя языка» у них нет.
 * Поэтому платформа подменяет язык по-своему (на desktop — через java.util.Locale,
 * см. jvmMain), а затем содержимое пересоздаётся, чтобы подписи перечитались.
 * Объявление здесь, а реализация в jvmMain: когда в следующих вехах вернутся web
 * и Android, им понадобятся свои реализации.
 *
 * Важно: всё, что должно пережить смену языка (стек экранов, тема), живёт выше этого вызова.
 */
@Composable
expect fun ProvideAppLocale(language: AppLanguage, content: @Composable () -> Unit)
