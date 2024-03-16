package core.auth.data.remote

import arrow.core.Either
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

class AuthServiceImpl(
    private val apiClient: ApiClient,
    private val authSettings: AuthSettings,
) : AuthService {

    override suspend fun loginPassword(
        username: String,
        password: String
    ): Either<Failure.NetworkFailure, LoginDto> {
        return apiClient.makeRequest(
            urlBuilder = URLBuilder(
                authSettings.getBaseUrl().first()
            ).apply { path("api/user/login") },
            methodType = HttpMethod.Post
        ) {
            setBody(Json.encodeToString(LoginPasswordOutDto.serializer(), LoginPasswordOutDto(username , password)))
        }
    }

    override suspend fun validateCode(
        number: String,
        code: String
    ): Either<Failure.NetworkFailure, LoginDto> {
        return apiClient.makeRequest(
            urlBuilder = URLBuilder(
                authSettings.getBaseUrl().first()
            ).apply { path("api/auth/validateCode") },
            methodType = HttpMethod.Post
        ) {
            setBody(Json.encodeToString(ValidateCodeOutDto.serializer(), ValidateCodeOutDto(number , code)))
        }
    }

    override suspend fun sendCode(
        number: String,
    ): Either<Failure.NetworkFailure, Unit> {
        return apiClient.makeRequest(
            urlBuilder = URLBuilder(
                authSettings.getBaseUrl().first()
            ).apply { path("api/auth/sendCode") },
            methodType = HttpMethod.Post
        ) {
            setBody(Json.encodeToString(LoginNumberOutDto.serializer(), LoginNumberOutDto(number)))
        }
    }

    override suspend fun signInForm(userDtoOut: UserDtoOut): Either<Failure.NetworkFailure, Unit> {
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