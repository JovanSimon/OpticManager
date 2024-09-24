package org.cyb.opticmanager.di

import org.cyb.opticmanager.db.AppDatabase
import org.cyb.opticmanager.db.getDatabaseBuilder
import org.cyb.opticmanager.initialScreen.InitialScreenViewModel
import org.cyb.opticmanager.initialScreen.repository.AppointmentRepository
import org.koin.dsl.module

actual fun platformModule() = module {
    single<AppDatabase> { getDatabaseBuilder() }
    single<AppointmentRepository> { AppointmentRepository(get()) }
    single { InitialScreenViewModel(get()) }
}