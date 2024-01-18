package core.auth.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import util.component.Form
import util.component.FormTextFieldData
import util.component.LeopardTextField
import util.component.LoadingButton
import util.helper.Validator
import util.helper.extractErrorMessage
import util.theme.black
import util.theme.onSurface
import util.theme.primaryColor
import util.theme.secondary
import util.theme.secondaryAlphaLow
import util.theme.splashGradiantEndColor
import util.theme.textColor
import util.theme.white

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AuthScreen(viewModel: AuthViewModel = koinInject()) {

    val loginOrSignUp = remember {
        mutableStateOf(true) // login
    }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).background(white)
            .padding(16.dp),
    ) {
        Image(
            painter = painterResource("illustration_health.jpg"),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(32.dp))
        Text(
            text = if (loginOrSignUp.value) "ورود" else "ثبت نام",
            style = MaterialTheme.typography.h4,
            color = black,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(16.dp))

        if (loginOrSignUp.value) {
            LoginPage(
                loginNumber = { number ->
                    viewModel.loginNumber()
                }, loginPassword = { username, password ->
                    viewModel.loginPassword(username , password)
                }, openSignUp = {
                    loginOrSignUp.value = false
                }
            )
        } else {
            SignUpPage() {
                loginOrSignUp.value = true
            }
        }
    }
}

@Composable
fun LoginPage(
    loginNumber: (String) -> Unit,
    loginPassword: (String, String) -> Unit,
    openSignUp: () -> Unit
) {
    val loginTypeState = remember {
        mutableStateOf(false) // number
    }

    Row(
        modifier = Modifier.fillMaxWidth()
            .background(shape = RoundedCornerShape(16.dp), color = secondaryAlphaLow)
            .padding(16.dp)
    ) {
        Text("با شماره موبایل", Modifier.weight(1f).background(
            if (loginTypeState.value) secondaryAlphaLow else secondary,
            shape = RoundedCornerShape(16.dp)
        ).clip(RoundedCornerShape(16.dp)).clickable { loginTypeState.value = false }
            .padding(8.dp), textAlign = TextAlign.Center, color = white)
        Spacer(Modifier.width(8.dp))
        Text(
            "با رمز عبور", Modifier.weight(1f).background(
                if (loginTypeState.value) secondary else secondaryAlphaLow,
                shape = RoundedCornerShape(16.dp)
            ).clip(RoundedCornerShape(16.dp)).clickable {
                loginTypeState.value = true
            }.padding(8.dp), textAlign = TextAlign.Center, color = white
        )
    }

    if (loginTypeState.value) {
        HealthUsernamePassword(
            buttonLoading = false,
            onLoginButtonClick = { username, password ->
//                login(username, password)
            })
    } else {
        HealthNumber(buttonLoading = false, onLoginButtonClick = { number ->
//                login(username, password)
        })
    }

    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = textColor,
                )
            ) {
                append("حساب کاربری ندارم. ")

            }
            withStyle(
                style = SpanStyle(
                    color = primaryColor,
                )
            ) {
                append("ثبت نام")
            }
        },
        Modifier.clip(RoundedCornerShape(16.dp)).clickable {
            openSignUp()
        }.padding(8.dp),
        textAlign = TextAlign.Center,
        color = secondary
    )
}

@Composable
fun SignUpPage(openLogin: () -> Unit) {
    HealthNumberSignUp(buttonLoading = false, onLoginButtonClick = { number ->
//                login(username, password)
    })

    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = textColor,
                )
            ) {
                append("حساب کاربری دارم. ")

            }
            withStyle(
                style = SpanStyle(
                    color = primaryColor,
                )
            ) {
                append("ورود")
            }
        }, Modifier.clip(RoundedCornerShape(16.dp)).clickable {
            openLogin()
        }.padding(8.dp), textAlign = TextAlign.Center, color = secondary
    )
}

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class,
    ExperimentalResourceApi::class
)
@Composable
fun HealthUsernamePassword(
    buttonLoading: Boolean,
    onLoginButtonClick: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {

    val focusRequester = remember { FocusRequester() }

    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val userNameValidators = listOf(Validator.NotEmpty(message = "نام کاربری خود را وارد کنید"))
    val passwordValidators = listOf(Validator.NotEmpty(message = "رمز عبور خود را وارد کنید"))
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    var usernameFieldErrorString: String? by remember {
        mutableStateOf(null)
    }
    var passwordFieldErrorString: String? by remember {
        mutableStateOf(null)
    }
    var isPasswordVisible = false


    Column(
        modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceBetween
    ) {

        val fieldsData = listOf(
            FormTextFieldData(
                label = "نام کاربری",
                value = username,
                scope = coroutineScope,
                leadingIcon = {
                    Image(
                        painter = painterResource("user.xml"),
                        "Person Icon",
                        colorFilter = ColorFilter.tint(textColor)
                    )
                },
                onKeyboardNextClicked = {
                    focusRequester.requestFocus()
                },
                onKeyboardDoneClicked = {},
                keyboardController = keyboardController,
                focusManager = focusManager,
                onValueChange = {
                    username = it
                    if (usernameFieldErrorString?.isNotEmpty() == true) {
                        usernameFieldErrorString =
                            userNameValidators.extractErrorMessage(username).orEmpty()
                    }
                },
                imeAction = ImeAction.Next,
                errorDescription = usernameFieldErrorString,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
            ), FormTextFieldData(
                label = "رمز عبور",
                value = password,
                scope = coroutineScope,
                singleLine = true,
                leadingIcon = {
                    Image(
                        painter = painterResource("ic_lock.xml"), "Lock Icon"
                    )
                },
                onKeyboardDoneClicked = {
                    password = it
                    if (passwordFieldErrorString?.isNotEmpty() == true) {
                        passwordFieldErrorString =
                            passwordValidators.extractErrorMessage(password).orEmpty()
                    }
                },
                focusRequester = focusRequester,
                isPassword = true,
                keyboardController = keyboardController,
                focusManager = focusManager,
                onValueChange = {
                    password = it
                    if (passwordFieldErrorString?.isNotEmpty() == true) {
                        passwordFieldErrorString =
                            passwordValidators.extractErrorMessage(password).orEmpty()
                    }
                },
                onTrailingIconClick = {
                    isPasswordVisible = !isPasswordVisible
                },
                isPasswordVisible = isPasswordVisible,
                errorDescription = passwordFieldErrorString
            )
        )
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Form(
                fields = fieldsData.toImmutableList(), modifier = Modifier.fillMaxWidth()
            ) { data ->
                LeopardTextField(
                    data, modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Spacer(modifier = Modifier.height(4.dp))
        }
        LoadingButton(
            onClick = {
                keyboardController?.hide()
                usernameFieldErrorString =
                    userNameValidators.extractErrorMessage(username).orEmpty()
                passwordFieldErrorString =
                    passwordValidators.extractErrorMessage(password).orEmpty()
                if (usernameFieldErrorString.isNullOrEmpty() && passwordFieldErrorString.isNullOrEmpty()) {
                    onLoginButtonClick(username, password)
                }
            },
            isLoading = buttonLoading,
            colors = ButtonDefaults.buttonColors(backgroundColor = splashGradiantEndColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "ورود", style = MaterialTheme.typography.button, color = white
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource("arrow_right.xml"),
                    contentDescription = "Login",
                    tint = white,
                    modifier = Modifier.rotate(180F)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

    }
}

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class,
    ExperimentalResourceApi::class
)
@Composable
fun HealthNumber(
    buttonLoading: Boolean, onLoginButtonClick: (String) -> Unit, modifier: Modifier = Modifier
) {

    val focusRequester = remember { FocusRequester() }

    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val userNameValidators = listOf(Validator.NotEmpty(message = "شماره موبایل خود را وارد کنید"))
    var username by remember {
        mutableStateOf("")
    }

    var usernameFieldErrorString: String? by remember {
        mutableStateOf(null)
    }


    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        val fieldsData = listOf(
            FormTextFieldData(
                label = "شماره موبایل",
                value = username,
                scope = coroutineScope,
                leadingIcon = {
                    Image(
                        painter = painterResource("user.xml"),
                        "Person Icon",
                        colorFilter = ColorFilter.tint(textColor)
                    )
                },
                onKeyboardNextClicked = {
                    focusRequester.requestFocus()
                },
                onKeyboardDoneClicked = {},
                focusRequester = focusRequester,
                keyboardController = keyboardController,
                focusManager = focusManager,
                onValueChange = {
                    username = it
                    if (usernameFieldErrorString?.isNotEmpty() == true) {
                        usernameFieldErrorString =
                            userNameValidators.extractErrorMessage(username).orEmpty()
                    }
                },
                imeAction = ImeAction.Done,
                errorDescription = usernameFieldErrorString,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )
        )
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Form(
                fields = fieldsData.toImmutableList(), modifier = Modifier.fillMaxWidth()
            ) { data ->
                LeopardTextField(
                    data, modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(32.dp))

        }
        LoadingButton(
            onClick = {
                keyboardController?.hide()
                usernameFieldErrorString =
                    userNameValidators.extractErrorMessage(username).orEmpty()
                if (usernameFieldErrorString.isNullOrEmpty()) {
                    onLoginButtonClick(username)
                }
            },
            isLoading = buttonLoading,
            colors = ButtonDefaults.buttonColors(backgroundColor = splashGradiantEndColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "ورود", style = MaterialTheme.typography.button, color = white
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource("arrow_right.xml"),
                    contentDescription = "Login",
                    tint = white,
                    modifier = Modifier.rotate(180F)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

    }
}

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class,
    ExperimentalResourceApi::class
)
@Composable
fun HealthNumberSignUp(
    buttonLoading: Boolean, onLoginButtonClick: (String) -> Unit, modifier: Modifier = Modifier
) {

    val focusRequester = remember { FocusRequester() }

    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val userNameValidators = listOf(Validator.NotEmpty(message = "شماره موبایل خود را وارد کنید"))
    var username by remember {
        mutableStateOf("")
    }

    var usernameFieldErrorString: String? by remember {
        mutableStateOf(null)
    }


    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        val fieldsData = listOf(
            FormTextFieldData(
                label = "شماره موبایل",
                value = username,
                scope = coroutineScope,
                leadingIcon = {
                    Image(
                        painter = painterResource("user.xml"),
                        "Person Icon",
                        colorFilter = ColorFilter.tint(textColor)
                    )
                },
                onKeyboardNextClicked = {
                    focusRequester.requestFocus()
                },
                onKeyboardDoneClicked = {},
                keyboardController = keyboardController,
                focusRequester = focusRequester,
                focusManager = focusManager,
                onValueChange = {
                    username = it
                    if (usernameFieldErrorString?.isNotEmpty() == true) {
                        usernameFieldErrorString =
                            userNameValidators.extractErrorMessage(username).orEmpty()
                    }
                },
                imeAction = ImeAction.Done,
                errorDescription = usernameFieldErrorString,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )
        )
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Form(
                fields = fieldsData.toImmutableList(), modifier = Modifier.fillMaxWidth()
            ) { data ->
                LeopardTextField(
                    data, modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(32.dp))

        }
        LoadingButton(
            onClick = {
                keyboardController?.hide()
                usernameFieldErrorString =
                    userNameValidators.extractErrorMessage(username).orEmpty()
                if (usernameFieldErrorString.isNullOrEmpty()) {
                    onLoginButtonClick(username)
                }
            },
            isLoading = buttonLoading,
            colors = ButtonDefaults.buttonColors(backgroundColor = splashGradiantEndColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "ثبت نام", style = MaterialTheme.typography.button, color = white
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource("arrow_right.xml"),
                    contentDescription = "Login",
                    tint = white,
                    modifier = Modifier.rotate(180F)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

    }
}