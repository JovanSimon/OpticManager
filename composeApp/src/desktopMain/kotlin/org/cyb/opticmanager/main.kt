package org.cyb.opticmanager

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import org.cyb.opticmanager.di.initKoin
import org.cyb.opticmanager.navigation.OpticNavigation

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "OpticManager",
            state = WindowState(width = 1300.dp, height = 900.dp)
        ) {
            OpticNavigation()
        }
    }
}