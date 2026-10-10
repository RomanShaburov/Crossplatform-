package org.example.project.ui.locale

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key

private external object window {
    var __customLocale: String?
}

@Composable
actual fun ProvideAppLocale(language: AppLanguage, content: @Composable () -> Unit) {
    window.__customLocale = language.tag
    key(language) { content() }
}
