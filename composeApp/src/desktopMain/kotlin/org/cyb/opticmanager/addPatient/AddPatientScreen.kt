package org.cyb.opticmanager.addPatient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val state by viewModel.state.collectAsState()

    AddPatientScreen(
        state = state,
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
            PatientForm(state, eventPublisher)
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
fun PatientForm(
    state: AddPatientContract.AddPatientUiState,
    eventPublisher: (uiEvent: AddPatientContract.AddPatientUiEvent) -> Unit
) {
    var showAdditionalForm by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0)) // Pozadinska boja
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter // Centriranje forme
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(0.8f) // Postavlja širinu kolone na 80% ekrana
                .background(Color.White, shape = MaterialTheme.shapes.medium)
                .padding(24.dp), // Unutrašnji padding za formu
            verticalArrangement = Arrangement.spacedBy(16.dp), // Razmak između redova
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "Unos pacijenta",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
            }

            // Prvi red sa "Ime" i "Prezime"
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text(text = "Ime:", fontWeight = FontWeight.SemiBold)
                        TextField(
                            value = state.name,
                            onValueChange = { eventPublisher(AddPatientContract.AddPatientUiEvent.NameChanged(it)) },
                            modifier = Modifier.fillMaxWidth(),
                            isError = state.nameError
                        )
                    }
                    Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                        Text(text = "Prezime:", fontWeight = FontWeight.SemiBold)
                        TextField(
                            value = state.lastname,
                            onValueChange = { eventPublisher(AddPatientContract.AddPatientUiEvent.LastnameChanged(it)) },
                            modifier = Modifier.fillMaxWidth(),
                            isError = state.lastnameError
                        )
                    }
                }
            }

            // Drugi red sa "Datum rođenja" i "Datum pregleda"
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text(text = "Datum rođenja:", fontWeight = FontWeight.SemiBold)
                        TextField(
                            value = state.dateOfBirth,
                            onValueChange = { eventPublisher(AddPatientContract.AddPatientUiEvent.DateOfBirthChanged(it)) },
                            modifier = Modifier.fillMaxWidth(),
                            isError = state.dateOfBirthError
                        )
                    }
                    Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                        Text(text = "Datum pregleda:", fontWeight = FontWeight.SemiBold)
                        TextField(
                            value = state.dateOfExam,
                            onValueChange = { eventPublisher(AddPatientContract.AddPatientUiEvent.DateOfExamChanged(it)) },
                            isError = state.dateOfExamError,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // Treći red sa "Mesto rođenja"
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Mesto rođenja:", fontWeight = FontWeight.SemiBold)
                    TextField(
                        value = state.placeOfBirth,
                        onValueChange = { eventPublisher(AddPatientContract.AddPatientUiEvent.PlaceOfBirthChanged(it)) },
                        modifier = Modifier.fillMaxWidth(),
                        isError = state.placeOfBirthError
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Dugme "More" za prikaz dodatne forme
            item {
                Button(
                    onClick = { showAdditionalForm = !showAdditionalForm },
                    modifier = Modifier
                        .fillMaxWidth(0.5f) // Širina dugmeta je 50% kolone
                        .height(48.dp)
                ) {
                    Text("More", fontSize = 16.sp)
                }
            }

            // Prikaz dodatne forme za dijagnostiku oka
            if (showAdditionalForm) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
                item {
                    Text(
                        text = "Dijagnostika oka",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary
                    )
                }
                item {
                    EyeDiagnosisForm(state, eventPublisher)
                }
            }
        }
    }
}

@Composable
fun EyeDiagnosisForm(
    state: AddPatientContract.AddPatientUiState,
    eventPublisher: (uiEvent: AddPatientContract.AddPatientUiEvent) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        // Sekcija za Daljinu
        Text(text = "Daljina", fontWeight = FontWeight.Bold, fontSize = 18.sp)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
            ) {
                Text(text = "OD SPH:")
                TextField(
                    value = state.odSphFar,
                    onValueChange = { eventPublisher(AddPatientContract.AddPatientUiEvent.OdSphFar(it)) },
                    modifier = Modifier.fillMaxWidth())
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(text = "OD CLY:")
                TextField(
                    value = state.odClyFar,
                    onValueChange = { eventPublisher(AddPatientContract.AddPatientUiEvent.OdClyFar(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(text = "OD AX:")
                TextField(
                    value = state.odAxFar,
                    onValueChange = { eventPublisher(AddPatientContract.AddPatientUiEvent.OdAxFar(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(text = "OS SPH:")
                TextField(
                    value = state.osSphFar,
                    onValueChange = { eventPublisher(AddPatientContract.AddPatientUiEvent.OsSphFar(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(text = "OS CLY:")
                TextField(
                    value = state.osClyFar,
                    onValueChange = { eventPublisher(AddPatientContract.AddPatientUiEvent.OsClyFar(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(text = "OS AX:")
                TextField(
                    value = state.osAxFar,
                    onValueChange = { eventPublisher(AddPatientContract.AddPatientUiEvent.OsAxFar(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "PD:")
            TextField(
                value = state.pdFar,
                onValueChange = { eventPublisher(AddPatientContract.AddPatientUiEvent.PdFar(it)) },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sekcija za Blizinu
        Text(
            text = "Blizina",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(text = "OD SPH:")
                TextField(
                    value = state.odSphClose,
                    onValueChange = { eventPublisher(AddPatientContract.AddPatientUiEvent.OdSphClose(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(text = "OD CLY:")
                TextField(
                    value = state.odClyClose,
                    onValueChange = { eventPublisher(AddPatientContract.AddPatientUiEvent.OdClyClose(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(text = "OD AX:")
                TextField(
                    value = state.odAxClose,
                    onValueChange = { eventPublisher(AddPatientContract.AddPatientUiEvent.OdAxClose(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(text = "OS SPH:")
                TextField(
                    value = state.osSphClose,
                    onValueChange = { eventPublisher(AddPatientContract.AddPatientUiEvent.OsSphClose(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(text = "OS CLY:")
                TextField(
                    value = state.osClyClose,
                    onValueChange = { eventPublisher(AddPatientContract.AddPatientUiEvent.OsClyClose(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(text = "OS AX:")
                TextField(
                    value = state.osAxClose,
                    onValueChange = { eventPublisher(AddPatientContract.AddPatientUiEvent.OsAxClose(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "PD:")
            TextField(
                value = state.pdClose,
                onValueChange = { eventPublisher(AddPatientContract.AddPatientUiEvent.PdClose(it)) },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Description:")
            TextField(
                value = state.description,
                onValueChange = { eventPublisher(AddPatientContract.AddPatientUiEvent.Description(it)) },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        val focusManager = LocalFocusManager.current

        Button(
            onClick = {
                eventPublisher(AddPatientContract.AddPatientUiEvent.SubmitButtonClicked("Clicked"))
                      focusManager.clearFocus()},
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Dodaj")
        }
    }
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