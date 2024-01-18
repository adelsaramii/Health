package util.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import util.theme.black
import util.theme.borderColor
import util.theme.gray
import util.theme.gray3
import util.theme.onSurface
import util.theme.primary
import util.theme.secondaryColor
import util.theme.textColor
import util.theme.white

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LeopardTextField(
    data: FormTextFieldData,
    modifier: Modifier = Modifier
) {
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
    var value by rememberSaveable(data.value) {
        mutableStateOf(data.value)
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(data.focusRequester)
                .padding(vertical = 2.dp),
            label = {
                Text(data.label, style = MaterialTheme.typography.caption)
            },
            isError = !data.errorDescription.isNullOrBlank(),
            leadingIcon = data.leadingIcon,
            visualTransformation = if (data.isPassword && !isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                if (data.isPassword) {
                    val description =
                        if (isPasswordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            if (isPasswordVisible)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff,
                            description
                        )
                    }
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = secondaryColor,
                focusedLabelColor = secondaryColor,
                containerColor = white,
                unfocusedBorderColor = borderColor,
            ),
            keyboardOptions = data.keyboardOptions,
            singleLine = data.singleLine,
            value = value,
            onValueChange = {
                if (data.singleLine && it.contains("\n"))
                    return@OutlinedTextField
                value = it
                data.onValueChange(it)

            },
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    data.onKeyboardDoneClicked(value)
                },
                onNext = {
                    data.onKeyboardNextClicked()
                }
            ),
            textStyle = MaterialTheme.typography.subtitle1.copy(textAlign = TextAlign.Left),
            shape = RoundedCornerShape(16.dp)
        )

        if (!data.errorDescription.isNullOrEmpty()) {
            Text(
                text = data.errorDescription,
                color = colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TextFieldComponent(
    modifier: Modifier = Modifier,
    placeholder: String = "",
    formItem: FormItem,
    onValueChange: (String) -> Unit = {}
) {
    var value by remember { mutableStateOf(formItem.stringValue ?: "") }

    LaunchedEffect(key1 = Unit) {
        onValueChange("")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (formItem.editable) 1f else 0.8f)
            .padding(8.dp)
    ) {
        Text(
            modifier = Modifier.padding(vertical = 4.dp),
            text = formItem.title,
            color = textColor,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.W500,
        )
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = gray, shape = RoundedCornerShape(8.dp)),
            value = value,
            isError = formItem.hasError,
            readOnly = !formItem.editable,
            textStyle = MaterialTheme.typography.body2,
            onValueChange = {
                value = it
                onValueChange(it)
            },
            placeholder = { androidx.compose.material.Text(text = placeholder, color = black, fontSize = 14.sp) },
            trailingIcon = {
                if (!formItem.editable) {
                    Image(
                        modifier = Modifier.padding(end = 4.dp),
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = "Lock",
                        colorFilter = ColorFilter.tint(gray3)
                    )
                }
            },
            leadingIcon = {
                Image(painterResource("ic_description.xml"), "description")
            },
            colors = androidx.compose.material.TextFieldDefaults.textFieldColors(
                backgroundColor = white,
                textColor = textColor,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = primary,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }

}

data class FormItem(
    val key: String,
    var title: String,
    val editable: Boolean,
    val required: Boolean,
    val requiredMessage: String,
    val orderIndex: Int,
    val component: String,
    val url: String? = null,
    val provider: String? = null,
    val stringValue: String? = null,
    val hasError: Boolean = false,
    val data: List<RequestSelectComponent>? = null,
)

data class RequestSelectComponent(
    val id: String = "",
    val title: String = "",
    var isSelected: Boolean = false,
)