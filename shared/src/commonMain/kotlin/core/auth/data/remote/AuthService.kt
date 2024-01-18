package core.auth.data.remote

import arrow.core.Either
import data.remote.Failure

interface AuthService {
    suspend fun login(username: String, password: String): Either<Failure.NetworkFailure, LoginDto>
}