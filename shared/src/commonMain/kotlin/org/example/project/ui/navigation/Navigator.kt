package org.example.project.ui.navigation

import androidx.compose.runtime.mutableStateListOf

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
