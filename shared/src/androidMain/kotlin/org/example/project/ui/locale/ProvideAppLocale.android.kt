package org.example.project.ui.locale

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.key
import androidx.compose.ui.platform.LocalConfiguration
import android.content.res.Configuration
import java.util.Locale

@Composable
actual fun ProvideAppLocale(language: AppLanguage, content: @Composable () -> Unit) {
    val configuration = Configuration(LocalConfiguration.current).apply {
        setLocale(Locale.forLanguageTag(language.tag))
    }
    CompositionLocalProvider(LocalConfiguration provides configuration) {
        key(language) { content() }
    }
}
