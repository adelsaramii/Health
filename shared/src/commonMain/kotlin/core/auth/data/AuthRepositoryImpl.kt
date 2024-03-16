package core.auth.data

import arrow.core.Either
import core.auth.data.remote.AuthService
import core.auth.data.remote.LoginDto
import core.auth.data.remote.UserDtoOut
import core.auth.domain.AuthRepository
import data.local.setting.AuthSettings
import data.remote.Failure
import util.helper.CommonFlow
import util.helper.asCommonFlow

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val authSetting: AuthSettings,
) : AuthRepository {
    override fun isLoggedIn(): CommonFlow<Boolean> {
        return authSetting.isLoggedIn().asCommonFlow()
    }

    override suspend fun loginPassword(
        username: String,
        password: String
    ): Either<Failure, LoginDto> {

        val result = authService.loginPassword(username, password)
        result.fold(ifRight = {
            authSetting.setAccessToken(it.token)
        }, ifLeft = {})
        return result
    }

    override suspend fun validateCode(
        number: String,
        code: String
    ): Either<Failure, LoginDto> {

        val result = authService.validateCode(number , code)
        result.fold(ifRight = {
            authSetting.setAccessToken(it.token)
        }, ifLeft = {})
        return result
    }

    override suspend fun sendCode(
        number: String
    ): Either<Failure, Unit> {
        return  authService.sendCode(number)
    }

    override suspend fun setLoggedIn(loggedIn: Boolean) {
        authSetting.setLoggedIn(loggedIn)
    }

    override suspend fun signInForm(userDtoOut: UserDtoOut): Either<Failure, Unit> {
        return  authService.signInForm(userDtoOut)
    }
}