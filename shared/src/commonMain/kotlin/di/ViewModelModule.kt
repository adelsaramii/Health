package di

import core.auth.presentation.AuthViewModel
import di.viewModelDefinition
import org.koin.dsl.module

val viewModelModule = module {

    viewModelDefinition {
        AuthViewModel(get())
    }

}