package org.example.project.ui.locale

import androidx.compose.runtime.Composable

enum class AppLanguage(val tag: String) {
    RU("ru"),
    EN("en");

    fun next(): AppLanguage = entries[(ordinal + 1) % entries.size]
}

@Composable
expect fun ProvideAppLocale(language: AppLanguage, content: @Composable () -> Unit)
