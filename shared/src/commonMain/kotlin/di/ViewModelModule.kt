package di

import core.auth.presentation.AuthViewModel
import core.main.home.HomeViewModel
import di.viewModelDefinition
import org.koin.dsl.module

val viewModelModule = module {

    viewModelDefinition {
        AuthViewModel(get())
    }

    viewModelDefinition {
        HomeViewModel(get())
    }

}