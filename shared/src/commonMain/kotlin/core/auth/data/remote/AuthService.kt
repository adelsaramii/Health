package core.auth.data.remote

import arrow.core.Either
import data.remote.Failure

interface AuthService {
    suspend fun loginPassword(username: String, password: String): Either<Failure.NetworkFailure, LoginDto>
    suspend fun loginNumber(number: String): Either<Failure.NetworkFailure, LoginDto>
}