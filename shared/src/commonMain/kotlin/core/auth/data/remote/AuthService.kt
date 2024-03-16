package core.auth.data.remote

import arrow.core.Either
import data.remote.Failure

interface AuthService {
    suspend fun loginPassword(
        username: String,
        password: String
    ): Either<Failure.NetworkFailure, LoginDto>

    suspend fun validateCode(
        number: String,
        code: String
    ): Either<Failure.NetworkFailure, LoginDto>

    suspend fun sendCode(number: String): Either<Failure.NetworkFailure, Unit>

    suspend fun signInForm(userDtoOut: UserDtoOut): Either<Failure.NetworkFailure, Unit>
}