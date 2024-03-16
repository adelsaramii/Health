package core.main.profile.domain

import arrow.core.Either
import core.auth.data.remote.UserDtoOut
import core.main.profile.data.remote.dto.UserDto
import data.remote.Failure

interface ProfileRepository {
    suspend fun getUser(): Either<Failure, UserDto>
    suspend fun saveUser(userDtoOut: UserDtoOut): Either<Failure, Unit>
}