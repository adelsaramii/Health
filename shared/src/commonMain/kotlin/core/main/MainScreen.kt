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
import core.base.MainNavigation
import io.github.xxfast.decompose.router.Router
import io.github.xxfast.decompose.router.rememberRouter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


@Composable
fun MainScreen() {

    val router: Router<MainNavigation> =
        rememberRouter(MainNavigation::class, stack = listOf(MainNavigation.Home))

    val currentDestination = remember {
        mutableStateOf(MainNavigation.Home)
    }


    Column(modifier = Modifier.fillMaxSize()) {

        Box(modifier = Modifier.fillMaxSize().background(Color.Gray)) {

        }

        Row(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 24.dp, bottom = 24.dp)
                .background(Color.Transparent)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AddItem(
                destination = MainNavigation.Home,
                currentDestination = currentDestination.value,
            )
            AddItem(
                destination = MainNavigation.Profile,
                currentDestination = currentDestination.value,
            )
        }
    }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AddItem(
    destination: MainNavigation,
    currentDestination: MainNavigation?,
) {
    val selected = destination == currentDestination

    val background =
        if (selected) MaterialTheme.colors.primary.copy(alpha = 0.6f) else Color.Transparent

    val contentColor =
        if (selected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .height(40.dp)
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = {

            })
    ) {
        Row(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp),
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
                Spacer(Modifier.width(4.dp))

                Text(
                    text = if (destination == MainNavigation.Home) "Home" else "Profile",
                    color = contentColor
                )
            }
        }
    }
}