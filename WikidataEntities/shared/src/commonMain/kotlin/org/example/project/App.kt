package org.example.project

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.runtime.entryProvider
import org.example.project.ui.WikidataViewModel
import org.example.project.ui.navigation.AppRoute
import org.example.project.ui.screens.EntityDetailsScreen
import org.example.project.ui.screens.EntityListScreen
import org.example.project.ui.theme.AppTheme

@Composable
fun App() {
    // Состояния каркаса (тема и язык)
    var isDark by rememberSaveable { mutableStateOf(false) }
    var language by rememberSaveable { mutableStateOf("ru") }

    // Принудительная смена локали на платформе (Desktop JVM)
    LaunchedEffect(language) {
        changeLanguageAtRuntime(language)
    }

    // key(language) заставляет Compose полностью пересоздать UI при смене языка,
    // что заставляет ресурсы перечитаться с учетом новой Locale.setDefault()
    key(language) {
        AppTheme(darkTheme = isDark) {
            val viewModel: WikidataViewModel = viewModel { WikidataViewModel() }
            val entities by viewModel.entities.collectAsState()
            
            val backStack = rememberSaveable(
                saver = listSaver(
                    save = { it.toList() },
                    restore = { mutableStateListOf(*it.toTypedArray()) }
                )
            ) { 
                mutableStateListOf<AppRoute>(AppRoute.EntityList) 
            }

            val provider = entryProvider<AppRoute> {
                entry<AppRoute.EntityList> {
                    EntityListScreen(
                        entities = entities,
                        onEntityClick = { id ->
                            backStack.add(AppRoute.EntityDetails(id))
                        },
                        isDark = isDark,
                        onToggleTheme = { isDark = !isDark },
                        language = language,
                        onToggleLanguage = { language = if (language == "ru") "en" else "ru" },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                entry<AppRoute.EntityDetails> { key ->
                    val entity = remember(key.entityId) {
                        viewModel.getEntity(key.entityId)
                    }
                    EntityDetailsScreen(
                        entity = entity,
                        onBackClick = {
                            if (backStack.size > 1) {
                                backStack.removeAt(backStack.size - 1)
                            }
                        },
                        isDark = isDark,
                        onToggleTheme = { isDark = !isDark },
                        language = language,
                        onToggleLanguage = { language = if (language == "ru") "en" else "ru" },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            NavDisplay(
                backStack = backStack.toList(),
                entryProvider = provider,
                onBack = {
                    if (backStack.size > 1) {
                        backStack.removeAt(backStack.size - 1)
                    }
                },
                transitionSpec = {
                    slideInHorizontally(animationSpec = tween(300)) { it } + fadeIn() togetherWith
                    slideOutHorizontally(animationSpec = tween(300)) { -it } + fadeOut()
                },
                popTransitionSpec = {
                    slideInHorizontally(animationSpec = tween(300)) { -it } + fadeIn() togetherWith
                    slideOutHorizontally(animationSpec = tween(300)) { it } + fadeOut()
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
