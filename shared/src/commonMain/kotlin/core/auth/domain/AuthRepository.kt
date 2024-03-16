package core.auth.domain

import arrow.core.Either
import core.auth.data.remote.LoginDto
import core.auth.data.remote.UserDtoOut
import data.remote.Failure
import util.helper.CommonFlow

interface AuthRepository {
    fun isLoggedIn(): CommonFlow<Boolean>
    suspend fun loginPassword(username: String, password: String): Either<Failure, LoginDto>
    suspend fun validateCode(number: String, code: String): Either<Failure, LoginDto>
    suspend fun sendCode(number: String): Either<Failure, Unit>
    suspend fun setLoggedIn(loggedIn: Boolean)
    suspend fun signInForm(userDtoOut: UserDtoOut): Either<Failure, Unit>
}