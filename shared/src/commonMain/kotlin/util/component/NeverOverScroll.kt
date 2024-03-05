package util.component

import androidx.compose.runtime.Composable

@Composable
expect fun NeverOverScroll(content: @Composable () -> Unit)