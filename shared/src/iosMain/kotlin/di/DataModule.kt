package di

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

@OptIn(ExperimentalSettingsApi::class)
actual fun dataPlatformModule(context: Any?) = module {

    // region setting

    single<FlowSettings> { NSUserDefaultsSettings(NSUserDefaults(suiteName = "HEALTH_SETTING")).toFlowSettings() }

    // endregion

    // region ktor

    single<HttpClientEngine> { Darwin.create() }

    // endregion

}