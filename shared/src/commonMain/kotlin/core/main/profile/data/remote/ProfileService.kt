package core.main.profile.data.remote

import arrow.core.Either
import core.auth.data.remote.UserDtoOut
import core.main.home.data.remote.dto.HealthInfoBulkDto
import core.main.profile.data.remote.dto.UserDto
import data.remote.Failure

interface ProfileService {
    suspend fun getUser(): Either<Failure, UserDto>
    suspend fun saveUser(userDtoOut: UserDtoOut): Either<Failure.NetworkFailure, Unit>
}