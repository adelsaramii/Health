package core.main.profile.data.remote

import arrow.core.Either
import core.auth.data.remote.UserDtoOut
import core.main.profile.data.remote.dto.UserDto
import data.local.setting.AuthSettings
import data.remote.ApiClient
import data.remote.Failure
import data.remote.makeRequest
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.URLBuilder
import io.ktor.http.path
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

class ProfileServiceImpl(
    private val apiClient: ApiClient,
    private val authSettings: AuthSettings
) : ProfileService {
    override suspend fun getUser(): Either<Failure, UserDto> {
        return apiClient.makeRequest(
            urlBuilder = URLBuilder(
                authSettings.getBaseUrl().first()
            ).apply { path("api/user/getUser") },
            methodType = HttpMethod.Get
        )
    }

    override suspend fun saveUser(userDtoOut: UserDtoOut): Either<Failure.NetworkFailure, Unit> {
        return apiClient.makeRequest(
            urlBuilder = URLBuilder(
                authSettings.getBaseUrl().first()
            ).apply { path("api/user/edit") },
            methodType = HttpMethod.Put
        ) {
            setBody(Json.encodeToString(UserDtoOut.serializer(), userDtoOut))
        }
    }

}