package di

import com.russhwolf.settings.ExperimentalSettingsApi
import com.squareup.sqldelight.db.SqlDriver
import core.auth.data.remote.AuthService
import core.auth.data.remote.AuthServiceImpl
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
        AuthServiceImpl()
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