package com.nexfin.frontend.ui.navigation

sealed interface AppDestination {
    data object Login : AppDestination
    data object Register : AppDestination
    data object Dashboard : AppDestination
    data object TopUp : AppDestination
    data object Transfer : AppDestination
    data object History : AppDestination
    data object Profile : AppDestination
}

object NavGraph {
    val startDestination: AppDestination = AppDestination.Login
}
