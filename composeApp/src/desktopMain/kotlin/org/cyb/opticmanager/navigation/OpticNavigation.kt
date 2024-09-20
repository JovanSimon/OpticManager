package org.cyb.opticmanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.cyb.opticmanager.initialScreen.initialScreen

@Composable
fun OpticNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "initialScreen",
    ) {
        initialScreen(
            route = "initialScreen",
            onUserClick = {
                navController.navigate("")
            }
        )
    }
}