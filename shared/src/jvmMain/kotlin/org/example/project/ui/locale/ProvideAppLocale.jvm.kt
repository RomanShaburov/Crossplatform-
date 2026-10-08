package org.example.project.ui.locale

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import java.util.Locale

@Composable
actual fun ProvideAppLocale(language: AppLanguage, content: @Composable () -> Unit) {
    Locale.setDefault(Locale.forLanguageTag(language.tag))
    key(language) { content() }
}
