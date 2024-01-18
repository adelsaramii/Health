package util.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope


@OptIn(ExperimentalFoundationApi::class)
data class FormTextFieldData @OptIn(ExperimentalComposeUiApi::class) constructor(
    val label: String,
    val scope: CoroutineScope,
    val value: String = "",
    val leadingIcon: @Composable (() -> Unit)? = null,
    val onKeyboardNextClicked: () -> Unit = { },
    val onTrailingIconClick: () -> Unit = { },
    val onKeyboardDoneClicked: (String) -> Unit = { },
    val onValueChange: (String) -> Unit = { },
    val relocationRequester: BringIntoViewRequester? = null,
    val keyboardType: KeyboardType = KeyboardType.Text,
    val keyboardOptions: KeyboardOptions =
        KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
    val imeAction: ImeAction = ImeAction.Next,
    val focusRequester: FocusRequester = FocusRequester(),
    val keyboardController: SoftwareKeyboardController? = null,
    val focusManager: FocusManager? = null,
    val errorDescription: String? = null,
    val isPassword: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val singleLine: Boolean = true,
)

@Composable
fun Form(
    fields: ImmutableList<FormTextFieldData>,
    modifier: Modifier = Modifier,
    itemContent: @Composable (FormTextFieldData) -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        fields.forEach {
            itemContent(it)
        }
    }
}
