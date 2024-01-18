package core.auth.domain

import arrow.core.Either
import core.auth.data.remote.LoginDto
import data.remote.Failure
import util.helper.CommonFlow

interface AuthRepository {
    fun isLoggedIn(): CommonFlow<Boolean>
    suspend fun loginPassword(username: String, password: String): Either<Failure, LoginDto>
    suspend fun loginNumber(number: String): Either<Failure, LoginDto>
    suspend fun setLoggedIn(loggedIn: Boolean)
}