import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import core.base.BaseScreen
import di.dataModule
import di.dataPlatformModule
import di.repositoryModule
import di.viewModelModule
import org.koin.compose.KoinApplication
import util.theme.HealthTheme

@Composable
fun App(context: Any? = null) {
    KoinApplication(application = {
        modules(dataPlatformModule(context), dataModule, repositoryModule, viewModelModule)
    }) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            HealthTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BaseScreen()
                }
            }
        }
    }
}