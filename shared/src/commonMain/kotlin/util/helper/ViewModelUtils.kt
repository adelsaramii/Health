package util.helper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.attendace.leopard.data.base.BaseViewModel

@Composable
fun <T : Any> BaseViewModel<T>.state(): State<T> {
    return stateStateFlow.collectAsState()
}