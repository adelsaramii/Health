package core.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.replaceCurrent
import core.auth.presentation.AuthScreen
import core.base.BaseNavigation
import core.base.MainNavigation
import core.main.home.HomeScreen
import core.main.profile.ProfileScreen
import core.splash.SplashScreen
import io.github.xxfast.decompose.router.Router
import io.github.xxfast.decompose.router.content.RoutedContent
import io.github.xxfast.decompose.router.rememberRouter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import util.theme.primary
import util.theme.white


@Composable
fun MainScreen() {

    val router: Router<MainNavigation> =
        rememberRouter(MainNavigation::class, stack = listOf(MainNavigation.Home))

    val currentDestination = remember {
        mutableStateOf<MainNavigation>(MainNavigation.Home)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier.fillMaxSize().background(white).align(Alignment.TopCenter)
                .padding(bottom = 72.dp)
        ) {
            RoutedContent(
                router = router,
            ) { screen ->
                when (screen) {
                    MainNavigation.Home -> {
                        currentDestination.value = MainNavigation.Home
                        HomeScreen()
                    }

                    MainNavigation.Profile -> {
                        currentDestination.value = MainNavigation.Profile
                        ProfileScreen()
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .background(white)
                .padding(top = 24.dp, bottom = 24.dp)
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(56.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomNavigation(
                destination = MainNavigation.Home,
                currentDestination = currentDestination.value
            ) {
                router.replaceCurrent(MainNavigation.Home)
            }
            bottomNavigation(
                destination = MainNavigation.Profile,
                currentDestination = currentDestination.value
            ) {
                router.replaceCurrent(MainNavigation.Profile)
            }
        }
    }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun bottomNavigation(
    destination: MainNavigation,
    currentDestination: MainNavigation?,
    onClick: () -> Unit
) {
    val selected = destination == currentDestination

    val background =
        if (selected) MaterialTheme.colors.primary.copy(alpha = 0.6f) else Color.Transparent

    val contentColor =
        if (selected) Color.White else primary

    Box(
        modifier = Modifier
            .height(44.dp)
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = {
                onClick()
            })
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = if (destination == MainNavigation.Home) painterResource(res = "icon_health.png") else painterResource(
                    res = "my_profile.xml"
                ),
                contentDescription = "icon",
                modifier = Modifier.size(24.dp),
                tint = contentColor
            )
            AnimatedVisibility(visible = selected) {
                Text(
                    text = if (destination == MainNavigation.Home) "خانه" else "پروفایل",
                    color = contentColor,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}