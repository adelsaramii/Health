package core.auth.data

import arrow.core.Either
import core.auth.data.remote.AuthService
import core.auth.data.remote.LoginDto
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

    override suspend fun loginNumber(
        number: String
    ): Either<Failure, LoginDto> {

        val result = authService.loginNumber(number)
        result.fold(ifRight = {
            authSetting.setAccessToken(it.token)
        }, ifLeft = {})
        return result
    }

    override suspend fun setLoggedIn(loggedIn: Boolean) {
        authSetting.setLoggedIn(loggedIn)
    }
}