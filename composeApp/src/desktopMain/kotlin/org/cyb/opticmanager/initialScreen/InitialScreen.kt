package org.cyb.opticmanager.initialScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.cyb.opticmanager.composableFunctions.NavigationItem
import org.cyb.opticmanager.composableFunctions.items
import org.cyb.opticmanager.db.dataModels.PatientData
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

    LaunchedEffect(state.selectedItemIndex) {

    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            SearchSection(eventPublisher)

            if (state.loadingPatients) {
                CircularProgressIndicator()
            } else {
                PatientGrid(patients = state.patients)
            }
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
fun SearchSection (eventPublisher: (uiEvent: InitialScreenContract.InitialScreenUiEvent) -> Unit) {
    var searchText by remember { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.20f)
            .padding(16.dp)
    ) {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .width(256.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { eventPublisher(InitialScreenContract.InitialScreenUiEvent.SearchPatients(searchText)) },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Pretraga")
        }
    }
}

@Composable
fun PatientGrid(patients: List<PatientData>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 300.dp),
            modifier = Modifier.widthIn(max = 1024.dp)
        ) {
            items(patients) { patient ->
                PatientCard(patient)
            }
        }
    }
}

@Composable
fun PatientCard(patient: PatientData) {
    Card (
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 6.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = "Ime: ${patient.name} ${patient.lastname}", style = MaterialTheme.typography.h6)
            Text(text = "Mesto rodjenja: ${patient.placeOfLiving}")
            Text(text = "Datum rodjenja: ${patient.dateOfBirth}")
        }
    }
}

@Composable
fun NavigationSideBar(
    items: List<NavigationItem>,
    selectedItemIndex: Int,
    onNavigate: (String) -> Unit,
    eventPublisher: (uiEvent: InitialScreenContract.InitialScreenUiEvent) -> Unit
) {
    NavigationRail{
        items.forEachIndexed { index, item ->
            NavigationRailItem(
                selected = selectedItemIndex == index,
                onClick = {
                    eventPublisher(InitialScreenContract.InitialScreenUiEvent.SelectedNavigationIndex(index))
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
