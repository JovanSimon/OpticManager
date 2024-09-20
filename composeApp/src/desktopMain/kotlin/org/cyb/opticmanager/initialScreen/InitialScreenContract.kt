package org.cyb.opticmanager.initialScreen

interface InitialScreenContract {
    data class InitialScreenUiState(
        val selectedItemIndex: Int = 0,
    )

    sealed class InitialScreenUiError {
        data class FaildToTest(val cause: Throwable? = null): InitialScreenUiError()
    }

    sealed class InitialScreenUiEvent {
        data class testHappend(val value: String): InitialScreenUiEvent()
    }
}