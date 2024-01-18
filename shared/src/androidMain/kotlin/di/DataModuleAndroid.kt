package di

import android.content.Context
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.SharedPreferencesSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

@OptIn(ExperimentalSettingsApi::class)
actual fun dataPlatformModule(context: Any?) = module {

    // region setting

    single {
        (context as Context).getSharedPreferences("HEALTH_SETTING", Context.MODE_PRIVATE)
    }
    single {
        SharedPreferencesSettings(get()).toFlowSettings()
    }

    // endregion

    // region ktor

    single<HttpClientEngine> {
        OkHttp.create()
    }

    // endregion

}