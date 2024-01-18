package core.base

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import core.auth.presentation.AuthScreen
import core.splash.SplashScreen
import io.github.xxfast.decompose.router.Router
import io.github.xxfast.decompose.router.content.RoutedContent
import io.github.xxfast.decompose.router.rememberRouter

@Composable
fun BaseScreen() {
    val baseRouter: Router<BaseNavigation> =
        rememberRouter(BaseNavigation::class, stack = listOf(BaseNavigation.Splash))

    val mainChild: Router<MainNavigation> =
        rememberRouter(MainNavigation::class, stack = listOf(MainNavigation.HealthInfo))

    RoutedContent(
        router = baseRouter,
    ) { screen ->
        when (screen) {
            BaseNavigation.Splash -> {
                SplashScreen {
                    if (it) {
                        baseRouter.replaceCurrent(BaseNavigation.Main)
                    } else {
                        baseRouter.replaceCurrent(BaseNavigation.Auth)
                    }
                }
            }

            BaseNavigation.Auth -> {
                AuthScreen()
            }

            BaseNavigation.Main -> {

            }
        }
    }
}

@Parcelize
sealed class BaseNavigation : Parcelable {
    object Splash : BaseNavigation()
    object Auth : BaseNavigation()
    object Main : BaseNavigation()
}

@Parcelize
sealed class MainNavigation : Parcelable {
    object Profile : MainNavigation()
    object HealthInfo : MainNavigation()
}