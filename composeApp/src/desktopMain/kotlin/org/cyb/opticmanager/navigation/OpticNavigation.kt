package org.cyb.opticmanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.cyb.opticmanager.addPatient.addPatient
import org.cyb.opticmanager.initialScreen.initialScreen

@Composable
fun OpticNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "initial_screen",
    ) {
        initialScreen(
            route = "initial_screen",
            onUserClick = {
                if (it.equals("add_patient")) {
                    navController.navigate(route = it)
                }
                else if (it.equals("appointments"))
                    navController.navigate(route = it)
            }
        )

        addPatient(
            route = "add_patient",
            onUserClick = {
                if (it.equals("initial_screen"))
                    navController.navigate(route = it)
                else if (it.equals("appointments"))
                    navController.navigate(route = it)
            }
        )
    }
}