package util.component

import androidx.compose.runtime.Composable

@Composable
actual fun NeverOverScroll(content: @Composable () -> Unit) {
    content()
}