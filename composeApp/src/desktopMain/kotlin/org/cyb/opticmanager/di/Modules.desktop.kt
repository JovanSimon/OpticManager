package org.cyb.opticmanager.di

import androidx.lifecycle.viewmodel.compose.viewModel
import org.cyb.opticmanager.addPatient.AddPatientViewModel
import org.cyb.opticmanager.addReportForPatient.AddReportViewModel
import org.cyb.opticmanager.db.AppDatabase
import org.cyb.opticmanager.db.getDatabaseBuilder
import org.cyb.opticmanager.initialScreen.InitialScreenViewModel
import org.cyb.opticmanager.initialScreen.repository.AppointmentRepository
import org.cyb.opticmanager.initialScreen.repository.DoctorReportRepository
import org.cyb.opticmanager.initialScreen.repository.PatientRepository
import org.cyb.opticmanager.patientDetails.PatientDetailsViewModel
import org.koin.dsl.module

actual fun platformModule() = module {
    single<AppDatabase> { getDatabaseBuilder() }
    single<AppointmentRepository> { AppointmentRepository(get()) }
    single<PatientRepository> { PatientRepository(get()) }
    single<DoctorReportRepository> { DoctorReportRepository(get()) }
    single { InitialScreenViewModel(get()) }
    single { AddPatientViewModel(get(), get()) }
    factory { (patiendId: String) -> PatientDetailsViewModel(patiendId, get(), get()) }
    factory { (patiendId: String) -> AddReportViewModel(patiendId, get()) }

}