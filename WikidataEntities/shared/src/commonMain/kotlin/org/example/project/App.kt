package org.example.project

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.ui.WikidataViewModel
import org.example.project.ui.navigation.AppRoute
import org.example.project.ui.navigation.WikidataNavHost
import org.example.project.ui.theme.AppTheme

@Composable
fun App() {
    var isDark by rememberSaveable { mutableStateOf(false) }
    var language by rememberSaveable { mutableStateOf("ru") }

    LaunchedEffect(language) {
        changeLanguageAtRuntime(language)
    }

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

            WikidataNavHost(
                backStack = backStack,
                entities = entities,
                getEntity = { id -> viewModel.getEntity(id) },
                isDark = isDark,
                onToggleTheme = { isDark = !isDark },
                language = language,
                onToggleLanguage = { language = if (language == "ru") "en" else "ru" },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
