package org.example.project.ui.navigation

import androidx.compose.runtime.mutableStateListOf

/**
 * Стек экранов. В Navigation 3 стек — обычный список, и он принадлежит нам:
 * открыть экран — добавить маршрут в конец, вернуться — убрать последний.
 *
 * Список наблюдаемый (`mutableStateListOf`): когда он меняется, NavDisplay
 * перерисовывается сам. Создаётся один раз в App, выше переключения языка, —
 * поэтому смена языка не сбрасывает стек.
 */
class Navigator {
    val backStack = mutableStateListOf<AppRoute>(AppRoute.EntityList)

    val canGoBack: Boolean get() = backStack.size > 1

    fun openEntity(id: String) {
        backStack.add(AppRoute.EntityDetails(id))
    }

    fun back() {
        if (canGoBack) backStack.removeAt(backStack.lastIndex)
    }
}
