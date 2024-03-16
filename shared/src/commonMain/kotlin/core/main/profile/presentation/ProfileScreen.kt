package core.main.profile.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import core.auth.data.remote.GenderEnum
import core.auth.data.remote.UserDtoOut
import core.auth.presentation.HealthGenderRadio
import core.auth.presentation.HealthSignUpForm
import core.main.home.presentation.HealthChart
import core.main.home.presentation.HealthInfoTypeEnum
import core.main.home.presentation.healthInfoDataToChart1
import core.main.home.presentation.healthInfoDataToChart2
import core.main.home.presentation.healthInfoDataToChart3
import core.main.home.presentation.healthInfoDataToChart4
import core.main.profile.data.remote.dto.UserDto
import core.main.profile.data.remote.dto.UserGender
import data.base.Loaded
import data.base.Loading
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
import util.helper.state
import util.theme.primaryColor
import util.theme.splashGradiantEndColor
import util.theme.textColor
import util.theme.white

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = koinInject()) {

    val state by viewModel.state()

    val loading = remember { mutableStateOf(true) }

    val selectedGender = remember { mutableStateOf(UserGender.Male) }

    LaunchedEffect(state.user) {
        if (state.user is Loaded) {
            loading.value = false
            selectedGender.value = state.user.data?.gender!!
        }
    }

    if (loading.value) {

        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

    } else {

        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                .background(white)
                .padding(16.dp),
        ) {
            Spacer(Modifier.height(16.dp))

            Text(
                text = "فرم اطلاعات",
                style = MaterialTheme.typography.h4,
                color = primaryColor,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(16.dp))

            HealthGenderRadio(
                gender = {
                    selectedGender.value = it
                },
                selectedGender
            )

            UserForm(
                message = "",
                userDto = state.user.data,
                buttonLoading = state.saveUser is Loading,
                onLoginButtonClick = { name, nationalCode, height, age, address, password ->
                    viewModel.saveUser(
                        UserDtoOut(
                            name = name,
                            nationalCode = nationalCode,
                            height = height,
                            age = age,
                            gender = selectedGender.value,
                            address = address,
                            password = password
                        )
                    )
                }
            )
        }

    }

}


@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class,
    ExperimentalResourceApi::class
)
@Composable
fun UserForm(
    message: String,
    userDto: UserDto?,
    buttonLoading: Boolean,
    onLoginButtonClick: (String, String, Int, Int, String, String) -> Unit,
    modifier: Modifier = Modifier
) {

    val focusRequester = remember { FocusRequester() }

    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val nameValidators = listOf(Validator.NotEmpty(message = message))
    var name by remember {
        mutableStateOf(userDto?.name.toString())
    }
    var nameErrorString: String? by remember {
        mutableStateOf(null)
    }

    val nationalCodeValidators = listOf(Validator.NotEmpty(message = message))
    var nationalCode by remember {
        mutableStateOf(userDto?.nationalCode.toString())
    }
    var nationalCodeErrorString: String? by remember {
        mutableStateOf(null)
    }

    val heightValidators = listOf(Validator.NotEmpty(message = message))
    var height by remember {
        mutableStateOf(userDto?.height.toString())
    }
    var heightErrorString: String? by remember {
        mutableStateOf(null)
    }

    val ageValidators = listOf(Validator.NotEmpty(message = message))
    var age by remember {
        mutableStateOf(userDto?.age.toString())
    }
    var ageErrorString: String? by remember {
        mutableStateOf(null)
    }

    val addressValidators = listOf(Validator.NotEmpty(message = message))
    var address by remember {
        mutableStateOf(userDto?.address.toString())
    }
    var addressErrorString: String? by remember {
        mutableStateOf(null)
    }

    val passwordValidators = listOf(Validator.NotEmpty(message = message))
    var password by remember {
        mutableStateOf(userDto?.password.toString())
    }
    var passwordErrorString: String? by remember {
        mutableStateOf(null)
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        val fieldsData = listOf(
            FormTextFieldData(
                label = "نام و نام خانوادگی",
                value = name,
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
                    name = it
                    if (nameErrorString?.isNotEmpty() == true) {
                        nameErrorString =
                            nameValidators.extractErrorMessage(name).orEmpty()
                    }
                },
                imeAction = ImeAction.Done,
                errorDescription = nameErrorString,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            ),
            FormTextFieldData(
                label = "کد ملی",
                value = nationalCode,
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
                    nationalCode = it
                    if (nationalCodeErrorString?.isNotEmpty() == true) {
                        nationalCodeErrorString =
                            nationalCodeValidators.extractErrorMessage(nationalCode).orEmpty()
                    }
                },
                imeAction = ImeAction.Done,
                errorDescription = nationalCodeErrorString,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                )
            ),
            FormTextFieldData(
                label = "قد",
                value = height,
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
                    height = it
                    if (heightErrorString?.isNotEmpty() == true) {
                        heightErrorString =
                            heightValidators.extractErrorMessage(height).orEmpty()
                    }
                },
                imeAction = ImeAction.Done,
                errorDescription = heightErrorString,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                )
            ),
            FormTextFieldData(
                label = "سن",
                value = age,
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
                    age = it
                    if (ageErrorString?.isNotEmpty() == true) {
                        ageErrorString =
                            ageValidators.extractErrorMessage(age).orEmpty()
                    }
                },
                imeAction = ImeAction.Done,
                errorDescription = ageErrorString,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                )
            ),
            FormTextFieldData(
                label = "آدرس",
                value = address,
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
                    address = it
                    if (addressErrorString?.isNotEmpty() == true) {
                        addressErrorString =
                            addressValidators.extractErrorMessage(address).orEmpty()
                    }
                },
                imeAction = ImeAction.Done,
                errorDescription = addressErrorString,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            ),
            FormTextFieldData(
                label = "پسوورد",
                value = password,
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
                    password = it
                    if (passwordErrorString?.isNotEmpty() == true) {
                        passwordErrorString =
                            passwordValidators.extractErrorMessage(password).orEmpty()
                    }
                },
                imeAction = ImeAction.Done,
                errorDescription = passwordErrorString,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )
        )
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

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
                nameErrorString =
                    nameValidators.extractErrorMessage(name).orEmpty()
                heightErrorString =
                    heightValidators.extractErrorMessage(height).orEmpty()
                addressErrorString =
                    addressValidators.extractErrorMessage(address).orEmpty()
                passwordErrorString =
                    passwordValidators.extractErrorMessage(password).orEmpty()
                ageErrorString =
                    ageValidators.extractErrorMessage(age).orEmpty()

                if (nameErrorString.isNullOrEmpty() && ageErrorString.isNullOrEmpty() && passwordErrorString.isNullOrEmpty() && addressErrorString.isNullOrEmpty() && heightErrorString.isNullOrEmpty()) {
                    onLoginButtonClick(
                        name,
                        nationalCode,
                        height.toInt(),
                        age.toInt(),
                        address,
                        password
                    )
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
                    "ذخیره", style = MaterialTheme.typography.button, color = white
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