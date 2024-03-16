package core.auth.presentation

import com.attendace.leopard.data.base.BaseViewModel
import core.auth.data.remote.UserDtoOut
import core.auth.domain.AuthRepository
import data.base.Failed
import data.base.LoadableData
import data.base.Loaded
import data.base.Loading
import data.base.NotLoaded
import data.remote.Failure
import di.loge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository,
) : BaseViewModel<AuthViewModel.State>(State()) {

    data class State(
        val isLogin: Boolean? = null,
        val number: String = "",
        val loginResponse: LoadableData<Unit> = NotLoaded,
        val sendCodeResponse: LoadableData<Unit> = NotLoaded,
        val validateCodeResponse: LoadableData<Unit> = NotLoaded,
        val signInForm: LoadableData<Unit> = NotLoaded,
        val snackbarError: Failure? = null
    )

    init {
        checkLogin()
    }

    private fun checkLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(3000)
            repository.isLoggedIn().collect { isLogin ->
                updateState {
                    copy(isLogin = isLogin)
                }
                return@collect
            }
        }
    }

    fun loginPassword(username: String, password: String) {
        updateState { copy(loginResponse = Loading) }
        viewModelScope.launch {
            repository.loginPassword(username, password).fold(
                ifRight = {
                    repository.setLoggedIn(true)
                    updateState { copy(loginResponse = Loaded(Unit)) }
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

    fun sendCode(number: String) {
        updateState { copy(sendCodeResponse = Loading) }
        viewModelScope.launch {
            repository.sendCode(number).fold(
                ifRight = {
                    updateState {
                        copy(
                            number = number,
                            sendCodeResponse = Loaded(Unit)
                        )
                    }
                }, ifLeft = {
                    updateState {
                        copy(
                            snackbarError = it,
                            sendCodeResponse = Failed(it)
                        )
                    }
                }
            )
        }
    }

    fun validateCode(code: String) {
        updateState { copy(validateCodeResponse = Loading) }
        viewModelScope.launch {
            repository.validateCode(currentState.number, code).fold(
                ifRight = {
                    repository.setLoggedIn(true)
                    updateState { copy(validateCodeResponse = Loaded(Unit)) }
                }, ifLeft = {
                    updateState {
                        copy(
                            snackbarError = it,
                            validateCodeResponse = Failed(it)
                        )
                    }
                }
            )
        }
    }

    fun signInForm(userDtoOut: UserDtoOut) {
        updateState { copy(signInForm = Loading) }
        viewModelScope.launch {
            repository.signInForm(userDtoOut).fold(
                ifRight = {
                    updateState { copy(signInForm = Loaded(Unit)) }
                }, ifLeft = {
                    updateState {
                        copy(
                            snackbarError = it,
                            signInForm = Failed(it)
                        )
                    }
                }
            )
        }
    }

}