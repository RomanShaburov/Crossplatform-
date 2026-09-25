package org.example.project.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.runtime.entryProvider
import org.example.project.domain.model.WikidataEntity
import org.example.project.ui.screens.EntityDetailsScreen
import org.example.project.ui.screens.EntityListScreen

@Composable
fun WikidataNavHost(
    backStack: SnapshotStateList<AppRoute>,
    entities: List<WikidataEntity>,
    getEntity: (String) -> WikidataEntity?,
    isDark: Boolean,
    onToggleTheme: () -> Unit,
    language: String,
    onToggleLanguage: () -> Unit,
    modifier: Modifier = Modifier
) {

    val provider = entryProvider<AppRoute> {
        entry<AppRoute.EntityList> {
            EntityListScreen(
                entities = entities,
                onEntityClick = { id -> backStack.add(AppRoute.EntityDetails(id)) },
                isDark = isDark,
                onToggleTheme = onToggleTheme,
                language = language,
                onToggleLanguage = onToggleLanguage,
                modifier = Modifier.fillMaxSize()
            )
        }
        entry<AppRoute.EntityDetails> { key ->
            val entity = remember(key.entityId) { getEntity(key.entityId) }
            EntityDetailsScreen(
                entity = entity,
                onBackClick = {
                    if (backStack.size > 1) backStack.removeAt(backStack.size - 1)
                },
                isDark = isDark,
                onToggleTheme = onToggleTheme,
                language = language,
                onToggleLanguage = onToggleLanguage,
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    NavDisplay(
        backStack = backStack.toList(),
        entryProvider = provider,
        onBack = {
            if (backStack.size > 1) backStack.removeAt(backStack.size - 1)
        },
        transitionSpec = {
            slideInHorizontally(animationSpec = tween(300)) { it } + fadeIn() togetherWith
                    slideOutHorizontally(animationSpec = tween(300)) { -it } + fadeOut()
        },
        popTransitionSpec = {
            slideInHorizontally(animationSpec = tween(300)) { -it } + fadeIn() togetherWith
                    slideOutHorizontally(animationSpec = tween(300)) { it } + fadeOut()
        },
        modifier = modifier
    )
}