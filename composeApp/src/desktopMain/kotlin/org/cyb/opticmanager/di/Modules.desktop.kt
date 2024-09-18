package org.cyb.opticmanager.di

import org.cyb.opticmanager.db.AppDatabase
import org.cyb.opticmanager.db.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule() = module {
    single<AppDatabase> { getDatabaseBuilder() }
}