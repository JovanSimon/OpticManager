package org.cyb.opticmanager.patientDetails

import DoctorReport
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.cyb.opticmanager.composableFunctions.AppIconButton
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

fun NavGraphBuilder.patientDetails(
    route: String,
    arguments: List<NamedNavArgument>,
    onUserClick: (String) -> Unit,
    onClose: () -> Unit
) = composable(
    route = route,
    arguments = arguments
) { navBackStackEntry ->
    val patientId = navBackStackEntry.arguments?.getString("patientId") ?: ""

    val viewModel: PatientDetailsViewModel = koinInject(parameters = { parametersOf(patientId) })
    val state by viewModel.state.collectAsState()

    PatientDetailsScreen(
        state = state,
        eventPublisher = {
            viewModel.setEvent(it)
        },
        onUserClick = onUserClick,
        onClose = onClose
    )
}

@Composable
fun PatientDetailsScreen(
    state: PatientDetailsContract.PatientDetailsUiState,
    eventPublisher: (uiEvent: PatientDetailsContract.PatientDetailsUiEvent) -> Unit,
    onClose: () -> Unit,
    onUserClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalji pacijenta") },
                navigationIcon = {
                    AppIconButton(
                        imageVector = Icons.Default.ArrowBack,
                        onClick = onClose
                    )
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                PatientInfoSection(
                    state = state,
                    onAddExamClick = { onUserClick(state.patientId) },
                    onEditClick = {}
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Izveštaji pregleda",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                )

                DoctorReportsGrid(reports = state.reports)
            }
        }
    )
}


@Composable
fun DoctorReportsGrid(reports: List<DoctorReport>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(reports) { report ->
            DoctorReportCard(report = report)
        }
    }
}

@Composable
fun DoctorReportCard(report: DoctorReport) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp,
        backgroundColor = Color(0xFFF5F5F5),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Izveštaj od: ${report.dateOfReport}",
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Daljina")
            Text("OD SPH: ${report.odSphFar}     OD CLY: ${report.odClyFar}     OD AX: ${report.odAxFar}")
            Text("OS SPH: ${report.osSphFar}     OS CLY: ${report.osClyFar}     OS AX: ${report.osAxFar}")
            Text("PD: ${report.pdFar}")
            Spacer(modifier = Modifier.height(8.dp))
            Text("Blizina")
            Text("OD SPH: ${report.odSphClose}     OD CLY: ${report.odClyClose}     OD AX: ${report.odAxClose}")
            Text("OS SPH: ${report.osSphClose}     OS CLY: ${report.osClyClose}     OS AX: ${report.osAxClose}")
            Text("PD: ${report.pdClose}")
            Spacer(modifier = Modifier.height(8.dp))
            Text("Opis: ${report.description}")
        }
    }
}

@Composable
fun PatientInfoSection(
    state: PatientDetailsContract.PatientDetailsUiState,
    onAddExamClick: () -> Unit,
    onEditClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0F0F0))
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
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
                Text(
                    text = "Informacije o pacijentu",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
            }

            item {
                InfoRow(label = "Ime:", value = state.name)
                InfoRow(label = "Prezime:", value = state.lastname)
                InfoRow(label = "Datum rođenja:", value = state.dateOfBirth)
                InfoRow(label = "Mesto rođenja:", value = state.placeOfBirth)
            }


            item {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = onAddExamClick,
                        modifier = Modifier.weight(1f).padding(end = 8.dp)
                    ) {
                        Text("Dodaj pregled")
                    }
                    Button(
                        onClick = onEditClick,
                        modifier = Modifier.weight(1f).padding(start = 8.dp)
                    ) {
                        Text("Izmeni")
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = label, fontWeight = FontWeight.SemiBold, color = Color.Gray)
        Text(
            text = value,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(top = 4.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}