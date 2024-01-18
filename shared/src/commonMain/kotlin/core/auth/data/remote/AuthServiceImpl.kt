package core.auth.data.remote

import arrow.core.Either
import data.local.setting.AuthSettings
import data.remote.ApiClient
import data.remote.Failure
import data.remote.makeRequest
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.URLBuilder
import io.ktor.http.path
import kotlinx.coroutines.flow.first

class AuthServiceImpl(
    private val apiClient: ApiClient,
    private val authSettings: AuthSettings,
) : AuthService {

    override suspend fun login(
        username: String,
        password: String
    ): Either<Failure.NetworkFailure, LoginDto> {
        return apiClient.makeRequest(
            urlBuilder = URLBuilder(
                authSettings.getBaseUrl().first()
            ).apply { path("user/login") },
            methodType = HttpMethod.Post
        ) {
            setBody(FormDataContent(
                Parameters.build {
                    append("username", username)
                    append("password", password)
                }
            ))
        }
    }

}