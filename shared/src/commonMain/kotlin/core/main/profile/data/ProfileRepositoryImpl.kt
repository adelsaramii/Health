package core.main.profile.data

import arrow.core.Either
import core.auth.data.remote.UserDtoOut
import core.main.home.data.remote.HomeService
import core.main.profile.data.remote.ProfileService
import core.main.profile.data.remote.dto.UserDto
import core.main.profile.domain.ProfileRepository
import data.remote.Failure

class ProfileRepositoryImpl(
    private val profileService: ProfileService
) : ProfileRepository {
    override suspend fun getUser(): Either<Failure, UserDto> {
        return profileService.getUser()
    }

    override suspend fun saveUser(userDtoOut: UserDtoOut): Either<Failure, Unit> {
        return  profileService.saveUser(userDtoOut)
    }
}