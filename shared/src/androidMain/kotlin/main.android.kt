import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.defaultComponentContext
import io.github.xxfast.decompose.LocalComponentContext

@Composable
fun MainView(appCompatActivity: AppCompatActivity) {
    val rootComponentContext: DefaultComponentContext = appCompatActivity.defaultComponentContext()
    CompositionLocalProvider(
        LocalComponentContext provides rootComponentContext
    ) {
        App(appCompatActivity)
    }
}