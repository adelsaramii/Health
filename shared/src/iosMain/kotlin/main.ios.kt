import androidx.compose.ui.window.ComposeUIViewController
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import io.github.xxfast.decompose.LocalComponentContext

fun MainViewController() = ComposeUIViewController {
    val lifecycle = LifecycleRegistry()
    val backDispatcher = BackDispatcher()

    val rootComponentContext = DefaultComponentContext(
        lifecycle = lifecycle,
        backHandler = backDispatcher
    )
    CompositionLocalProvider(LocalComponentContext provides rootComponentContext) {
        App()
    }
}