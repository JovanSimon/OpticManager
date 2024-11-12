package org.cyb.opticmanager.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.cyb.opticmanager.addPatient.addPatient
import org.cyb.opticmanager.addReportForPatient.addReport
import org.cyb.opticmanager.initialScreen.initialScreen
import org.cyb.opticmanager.patientDetails.patientDetails

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
                else if (it.equals("initial_screen")) {

                }
                else if (it.equals("appointments"))
                    navController.navigate(route = it)
                else {
                    navController.navigate(route = "patients/$it")
                }
            }
        )

        patientDetails(
            route = "patients/{patientId}",
            arguments = listOf(
                navArgument(name = "patientId") {
                    nullable = false
                    type = NavType.StringType
                }
            ),
            onUserClick = {
                navController.navigate(route = "report/$it")
            },
            onClose = {
                navController.navigateUp()
            }
        )

        addReport(
            route = "report/{patientId}",
            arguments = listOf(
                navArgument(name = "patientId") {
                    nullable = false
                    type = NavType.StringType
                }
            ),
            onUserClick = {
                navController.navigate(route = "patients/$it")
            },
            onClose = {
                navController.navigateUp()
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

inline val SavedStateHandle.patientId: Long
    get() = checkNotNull(get("patientId")) {"patientId is mandatory"}