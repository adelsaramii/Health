package core.main.profile.presentation

import com.attendace.leopard.data.base.BaseViewModel
import core.auth.data.remote.UserDtoOut
import core.main.profile.data.remote.dto.UserDto
import core.main.profile.domain.ProfileRepository
import data.base.Failed
import data.base.LoadableData
import data.base.Loaded
import data.base.Loading
import data.base.NotLoaded
import data.remote.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: ProfileRepository,
) : BaseViewModel<ProfileViewModel.State>(State()) {

    data class State(
        val user: LoadableData<UserDto> = NotLoaded,
        val saveUser: LoadableData<Unit> = NotLoaded,
        val snackbarError: Failure? = null
    )

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUser().fold(
                ifRight = {
                    updateState {
                        copy(
                            user = Loaded(it)
                        )
                    }
                }, ifLeft = {
                    updateState {
                        copy(
                            snackbarError = it,
                        )
                    }
                }
            )
        }
    }

    fun saveUser(userDtoOut: UserDtoOut) {
        updateState { copy(saveUser = Loading) }
        viewModelScope.launch {
            repository.saveUser(userDtoOut).fold(
                ifRight = {
                    updateState { copy(saveUser = Loaded(Unit)) }
                }, ifLeft = {
                    updateState {
                        copy(
                            snackbarError = it,
                            saveUser = Failed(it)
                        )
                    }
                }
            )
        }
    }
}