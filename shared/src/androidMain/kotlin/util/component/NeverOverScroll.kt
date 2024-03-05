package util.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@OptIn(ExperimentalFoundationApi::class)
@Composable
actual fun NeverOverScroll(content: @Composable () -> Unit) {
    (CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        content.invoke()
    })
}