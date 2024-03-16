package di

import com.russhwolf.settings.ExperimentalSettingsApi
import com.squareup.sqldelight.db.SqlDriver
import core.auth.data.remote.AuthService
import core.auth.data.remote.AuthServiceImpl
import core.main.home.data.remote.HomeService
import core.main.home.data.remote.HomeServiceImpl
import core.main.profile.data.remote.ProfileService
import core.main.profile.data.remote.ProfileServiceImpl
import data.local.setting.AuthSettings
import data.local.setting.AuthSettingsImpl
import data.remote.ApiClient
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

expect fun dataPlatformModule(context: Any?): Module

val dataModule = module {

    // region ktor

    single {
        ApiClient(
            get(),get()
        )
    }

    // endregion

    // region service

    single<AuthService> {
        AuthServiceImpl(get() , get())
    }

    single<HomeService> {
        HomeServiceImpl(get() , get())
    }

    single<ProfileService> {
        ProfileServiceImpl(get() , get())
    }

    // endregion

    // region setting

    @OptIn(ExperimentalSettingsApi::class)
    single<AuthSettings> {
        AuthSettingsImpl(
            get()
        )
    }

    // endregion

}