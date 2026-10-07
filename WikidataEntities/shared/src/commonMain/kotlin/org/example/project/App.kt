package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.example.project.data.MockWikidataRepository
import org.example.project.domain.repository.WikidataRepository
import org.example.project.ui.components.AppScaffold
import org.example.project.ui.locale.AppLanguage
import org.example.project.ui.locale.ProvideAppLocale
import org.example.project.ui.navigation.AppNavDisplay
import org.example.project.ui.navigation.AppRoute
import org.example.project.ui.navigation.Navigator
import org.example.project.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource
import wikidataentities.shared.generated.resources.*

@Composable
fun App() {
    // Настройки сессии: живут в памяти и на диск не пишутся (сохранение — веха В3).
    var darkTheme by remember { mutableStateOf(false) }
    var language by remember { mutableStateOf(AppLanguage.RU) }

    // Создаются один раз и выше ProvideAppLocale: смена языка пересоздаёт
    // экраны, но стек и данные при этом остаются прежними.
    val repository: WikidataRepository = remember { MockWikidataRepository() }
    val navigator = remember { Navigator() }

    AppTheme(darkTheme = darkTheme) {
        ProvideAppLocale(language) {
            val title = when (navigator.backStack.last()) {
                AppRoute.EntityList -> stringResource(Res.string.list_title)
                is AppRoute.EntityDetails -> stringResource(Res.string.details_title)
            }
            AppScaffold(
                title = title,
                onBack = navigator::back.takeIf { navigator.canGoBack },
                darkTheme = darkTheme,
                onToggleTheme = { darkTheme = !darkTheme },
                onToggleLanguage = { language = language.next() },
            ) { modifier ->
                AppNavDisplay(
                    navigator = navigator,
                    repository = repository,
                    modifier = modifier,
                )
            }
        }
    }
}
