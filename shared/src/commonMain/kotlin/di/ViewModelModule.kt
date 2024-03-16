package di

import core.auth.presentation.AuthViewModel
import core.main.home.presentation.HomeViewModel
import core.main.profile.presentation.ProfileViewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModelDefinition {
        AuthViewModel(get())
    }

    viewModelDefinition {
        HomeViewModel(get())
    }

    viewModelDefinition {
        ProfileViewModel(get())
    }

}