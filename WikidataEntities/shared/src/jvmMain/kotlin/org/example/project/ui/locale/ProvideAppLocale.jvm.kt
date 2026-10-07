package org.example.project.ui.locale

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import java.util.Locale

@Composable
actual fun ProvideAppLocale(language: AppLanguage, content: @Composable () -> Unit) {
    // На desktop ресурсы читают язык из Locale.getDefault().
    Locale.setDefault(Locale.forLanguageTag(language.tag))
    // key пересоздаёт содержимое при смене языка, и stringResource перечитывает подписи.
    key(language) { content() }
}
