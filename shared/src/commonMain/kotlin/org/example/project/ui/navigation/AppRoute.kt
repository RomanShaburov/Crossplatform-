package org.example.project.ui.navigation

import kotlinx.serialization.Serializable
import androidx.navigation3.runtime.NavKey

@Serializable
sealed interface AppRoute : NavKey {
    @Serializable
    data object EntityList : AppRoute
    
    @Serializable
    data class EntityDetails(val entityId: String) : AppRoute
}
