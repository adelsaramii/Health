package core.auth.presentation

import com.attendace.leopard.data.base.BaseViewModel
import core.auth.data.remote.LoginDto
import core.auth.domain.AuthRepository
import data.base.Failed
import data.base.LoadableData
import data.base.Loaded
import data.base.Loading
import data.base.NotLoaded
import data.remote.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository,
) : BaseViewModel<AuthViewModel.State>(State()) {

    data class State(
        val isLogin: Boolean? = null,
        val loginResponse: LoadableData<LoginDto> = NotLoaded,
        val snackbarError: Failure? = null
    )

    init {
        checkLogin()
    }

    private fun checkLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(3000)
            repository.isLoggedIn().collect { isLogin ->
                if (!isLogin) {
                    updateState {
                        copy(isLogin = isLogin)
                    }
                    return@collect
                }
            }
        }
    }

    fun loginPassword(username: String, password: String) {
        updateState { copy(loginResponse = Loading) }
        viewModelScope.launch {
            repository.loginPassword(username, password).fold(
                ifRight = {
                    updateState { copy(loginResponse = Loaded(it)) }
                }, ifLeft = {
                    updateState {
                        copy(
                            snackbarError = it,
                            loginResponse = Failed(it)
                        )
                    }
                }
            )
        }
    }

}