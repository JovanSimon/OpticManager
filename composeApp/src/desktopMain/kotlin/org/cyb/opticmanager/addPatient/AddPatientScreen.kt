package org.cyb.opticmanager.addPatient

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.cyb.opticmanager.composableFunctions.NavigationItem
import org.cyb.opticmanager.composableFunctions.items
import org.koin.compose.koinInject

fun NavGraphBuilder.addPatient(
    route: String,
    onUserClick: (String) -> Unit
) = composable(
    route = route
) {
    val viewModel: AddPatientViewModel = koinInject()
    val state = viewModel.state.collectAsState()

    AddPatientScreen(
        state = state.value,
        eventPublisher = {
            viewModel.setEvent(it)
        },
        onUserClick = onUserClick
    )
}

@Composable
fun AddPatientScreen(
    state: AddPatientContract.AddPatientUiState,
    eventPublisher: (uiEvent: AddPatientContract.AddPatientUiEvent) -> Unit,
    onUserClick: (String) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(text = "NAHHHHHHHHHHHHHHH")
        }
    }

    NavigationSideBar(
        items = items,
        selectedItemIndex = state.selectedItemIndex,
        onNavigate = {
            onUserClick(it)
        },
        eventPublisher = eventPublisher
    )
}

@Composable
fun NavigationSideBar(
    items: List<NavigationItem>,
    selectedItemIndex: Int,
    onNavigate: (String) -> Unit,
    eventPublisher: (uiEvent: AddPatientContract.AddPatientUiEvent) -> Unit
) {
    NavigationRail{
        items.forEachIndexed { index, item ->
            NavigationRailItem(
                selected = selectedItemIndex == index,
                onClick = {
                    eventPublisher(AddPatientContract.AddPatientUiEvent.SelectedNavigationIndex(index))
                    println(index)
                    when (index) {
                        0 -> onNavigate("initial_screen")
                        1 -> onNavigate("add_patient")
                        2 -> onNavigate("appointments")
                    }
                },
                icon = {
                    NavigationIcon(
                        item = item,
                        selected = selectedItemIndex == index
                    )
                },
                label = {
                    Text(text = item.title)
                },
            )
        }
    }
}

@Composable
fun NavigationIcon(
    item: NavigationItem,
    selected: Boolean
) {
    BadgedBox(
        badge = {
            if (item.badgeCount != null) {
                Badge {
                    Text(text = item.badgeCount.toString())
                }
            } else if (item.hasNews) {
                Badge()
            }
        }
    ) {
        Icon(
            imageVector = if(selected) item.selectedIcon else item.unselectedIcon,
            contentDescription = item.title
        )
    }
}