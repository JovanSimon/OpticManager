package org.cyb.opticmanager

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.cyb.opticmanager.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "OpticManager"
        ) {
            App()
        }
    }
}