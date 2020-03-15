package com.caminero.newton.core.utils

import androidx.navigation.NavDirections

sealed class NavigationCommand {

    data class To(val direction: NavDirections) : NavigationCommand()

    object Back : NavigationCommand()

    data class BackTo(val resourceId: Int) : NavigationCommand()

    data class Open(val resourceId: Int) : NavigationCommand()

    object ToRoot : NavigationCommand()
}