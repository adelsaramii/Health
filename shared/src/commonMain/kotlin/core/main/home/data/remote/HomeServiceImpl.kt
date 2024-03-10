package core.main.home.data.remote

import arrow.core.Either
import core.auth.data.remote.LoginNumberOutDto
import core.main.home.data.remote.dto.HealthInfoBulkDto
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

class HomeServiceImpl(
    private val apiClient: ApiClient,
    private val authSettings: AuthSettings,
) : HomeService {

    override suspend fun healthInfoBulk(): Either<Failure, HealthInfoBulkDto> {
        return apiClient.makeRequest(
            urlBuilder = URLBuilder(
                authSettings.getBaseUrl().first()
            ).apply { path("api/healthInfo/BulkList") },
            methodType = HttpMethod.Get
        )
    }

}