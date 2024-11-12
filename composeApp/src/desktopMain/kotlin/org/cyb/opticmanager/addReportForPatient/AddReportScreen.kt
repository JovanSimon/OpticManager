package org.cyb.opticmanager.addReportForPatient

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.cyb.opticmanager.addPatient.AddPatientContract
import org.cyb.opticmanager.composableFunctions.AppIconButton
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

fun NavGraphBuilder.addReport(
    route: String,
    arguments: List<NamedNavArgument>,
    onUserClick: (String) -> Unit,
    onClose: () -> Unit
) = composable(
    route = route,
    arguments = arguments
) { navBackStackEntry ->
    val patientId = navBackStackEntry.arguments?.getString("patientId") ?: ""

    val viewModel: AddReportViewModel = koinInject(parameters = { parametersOf(patientId) })
    val state by viewModel.state.collectAsState()

    AddReportScreen(
        state = state,
        eventPublisher = {
            viewModel.setEvent(it)
        },
        onUserClick = onUserClick,
        onClose = onClose
    )
}

@Composable
fun AddReportScreen(
    state: AddReportContract.AddReportUiState,
    eventPublisher: (uiEvent: AddReportContract.AddReportUiEvent) -> Unit,
    onUserClick: (String) -> Unit,
    onClose: () -> Unit
) {
    println("navigate: ${state.navigateBack} patientId: ${state.patientId}")
    if(state.navigateBack && !state.patientId.equals("")) {
        onUserClick(state.patientId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dodaj nov pregled") },
                navigationIcon = {
                    AppIconButton(
                        imageVector = Icons.Default.ArrowBack,
                        onClick = onClose
                    )
                }
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center // Centrira sadržaj unutar Box-a
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .background(Color.White, shape = MaterialTheme.shapes.medium)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        EyeDiagnosisForm(state, eventPublisher)
                    }
                }
            }
        }
    )
}

@Composable
fun EyeDiagnosisForm(
    state: AddReportContract.AddReportUiState,
    eventPublisher: (uiEvent: AddReportContract.AddReportUiEvent) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

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
                    onValueChange = { eventPublisher(AddReportContract.AddReportUiEvent.OdSphFar(it)) },
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
                    onValueChange = { eventPublisher(AddReportContract.AddReportUiEvent.OdClyFar(it)) },
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
                    onValueChange = { eventPublisher(AddReportContract.AddReportUiEvent.OdAxFar(it)) },
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
                    onValueChange = { eventPublisher(AddReportContract.AddReportUiEvent.OsSphFar(it)) },
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
                    onValueChange = { eventPublisher(AddReportContract.AddReportUiEvent.OsClyFar(it)) },
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
                    onValueChange = { eventPublisher(AddReportContract.AddReportUiEvent.OsAxFar(it)) },
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
                onValueChange = { eventPublisher(AddReportContract.AddReportUiEvent.PdFar(it)) },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


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
                    onValueChange = { eventPublisher(AddReportContract.AddReportUiEvent.OdSphClose(it)) },
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
                    onValueChange = { eventPublisher(AddReportContract.AddReportUiEvent.OdClyClose(it)) },
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
                    onValueChange = { eventPublisher(AddReportContract.AddReportUiEvent.OdAxClose(it)) },
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
                    onValueChange = { eventPublisher(AddReportContract.AddReportUiEvent.OsSphClose(it)) },
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
                    onValueChange = { eventPublisher(AddReportContract.AddReportUiEvent.OsClyClose(it)) },
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
                    onValueChange = { eventPublisher(AddReportContract.AddReportUiEvent.OsAxClose(it)) },
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
                onValueChange = { eventPublisher(AddReportContract.AddReportUiEvent.PdClose(it)) },
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
                onValueChange = { eventPublisher(AddReportContract.AddReportUiEvent.Description(it)) },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        val focusManager = LocalFocusManager.current

        Column(
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Datum pregleda:")
            TextField(
                value = state.dateOfExam,
                onValueChange = { eventPublisher(AddReportContract.AddReportUiEvent.DateOfExamChanged(it)) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Button(
            onClick = {
                eventPublisher(AddReportContract.AddReportUiEvent.SubmitButtonClicked("Clicked"))
                focusManager.clearFocus()},
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Dodaj")
        }
    }
}