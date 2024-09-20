package org.cyb.opticmanager.initialScreen.di.modules

import org.cyb.opticmanager.initialScreen.InitialScreenViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val initialScreenModules = module {
    single<InitialScreenViewModel> { get() }
}