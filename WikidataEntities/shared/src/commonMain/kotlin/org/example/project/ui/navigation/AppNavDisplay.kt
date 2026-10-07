package org.example.project.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import org.example.project.domain.repository.WikidataRepository
import org.example.project.ui.detail.EntityDetailScreen
import org.example.project.ui.detail.EntityDetailViewModel
import org.example.project.ui.list.EntityListScreen
import org.example.project.ui.list.EntityListViewModel

private const val TRANSITION_MS = 300

@Composable
fun AppNavDisplay(
    navigator: Navigator,
    repository: WikidataRepository,
    modifier: Modifier = Modifier,
) {
    NavDisplay(
        backStack = navigator.backStack,
        modifier = modifier,
        onBack = navigator::back,
        transitionSpec = { slide(forward = true) },
        popTransitionSpec = { slide(forward = false) },
        predictivePopTransitionSpec = { slide(forward = false) },
        entryProvider = entryProvider {
            entry<AppRoute.EntityList> {
                val viewModel = viewModel {
                    EntityListViewModel(repository, onOpenEntity = navigator::openEntity)
                }
                val state by viewModel.state.collectAsStateWithLifecycle()
                EntityListScreen(state = state, onIntent = viewModel::onIntent)
            }
            entry<AppRoute.EntityDetails> { route ->
                val viewModel = viewModel(key = "details-${route.entityId}") {
                    EntityDetailViewModel(route.entityId, repository)
                }
                val state by viewModel.state.collectAsStateWithLifecycle()
                EntityDetailScreen(state = state)
            }
        },
    )
}

private fun AnimatedContentTransitionScope<*>.slide(forward: Boolean): ContentTransform {
    val sign = if (forward) 1 else -1
    val enter = slideInHorizontally(tween(TRANSITION_MS)) { width -> sign * width } + fadeIn(tween(TRANSITION_MS))
    val exit = slideOutHorizontally(tween(TRANSITION_MS)) { width -> -sign * width } + fadeOut(tween(TRANSITION_MS))
    return enter togetherWith exit
}
