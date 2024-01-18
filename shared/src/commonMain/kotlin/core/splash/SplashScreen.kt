package core.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.auth.presentation.AuthViewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import util.helper.state
import util.theme.neutralLight8Dark2
import util.theme.primaryColor
import util.theme.white

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SplashScreen(
    viewModel: AuthViewModel = koinInject(),
    isLogin: (Boolean) -> Unit
) {
    val state by viewModel.state()

    LaunchedEffect(state.isLogin) {
        state.isLogin.let {
            if (it != null) {
                isLogin(it)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(primaryColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource("health_splash.png"),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
        )
        Spacer(modifier = Modifier.height(120.dp))

        Text(
            text = "سلامت",
            fontSize = 60.sp,
            color = white
        )

        Text(
            text = "محصولی از گروه هامین",
            fontSize = 17.sp,
            color = neutralLight8Dark2,
        )
    }
}