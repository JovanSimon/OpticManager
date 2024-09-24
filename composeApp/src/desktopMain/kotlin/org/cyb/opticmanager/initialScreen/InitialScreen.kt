package org.cyb.opticmanager.initialScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.cyb.opticmanager.composableFunctions.NavigationItem
import org.cyb.opticmanager.composableFunctions.items
import org.koin.compose.koinInject

fun NavGraphBuilder.initialScreen(
    route: String,
    onUserClick: (String) -> Unit
) = composable(
    route = route
) {
    val viewModel: InitialScreenViewModel = koinInject()
    val state = viewModel.state.collectAsState()

    InitialScreen(
        state = state.value,
        eventPublisher = {
            viewModel.setEvent(it)
        },
        onUserClick = onUserClick
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InitialScreen (
    state: InitialScreenContract.InitialScreenUiState,
    eventPublisher: (uiEvent: InitialScreenContract.InitialScreenUiEvent) -> Unit,
    onUserClick: (String) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(
                    start = 80.dp
                )
        ) {
            items(1) {
                Text(
                    text = state.test,
                )
            }
        }
    }

    NavigationSideBar(
        items = items,
        selectedItemIndex = state.selectedItemIndex,
        onNavigate = { TODO() }
    )
}

@Composable
fun NavigationSideBar(
    items: List<NavigationItem>,
    selectedItemIndex: Int,
    onNavigate: (Int) -> Unit
) {
    NavigationRail{
        items.forEachIndexed { index, item ->
            NavigationRailItem(
                selected = selectedItemIndex == index,
                onClick = { onNavigate(index) },
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
