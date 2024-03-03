package data.remote

import arrow.core.Either
import data.local.setting.AuthSettings
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.first
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json

class MissingPageException(response: HttpResponse, cachedResponseText: String) :
    ResponseException(response, cachedResponseText) {
    override val message: String = "Missing page: ${response.call.request.url}. " +
            "Status: ${response.status}."
}


class ApiClient(
    engine: HttpClientEngine,
    val authSettings: AuthSettings,
) {
    val client = HttpClient(engine) {
        expectSuccess = true
        install(ContentNegotiation) {
            json(Json {
                encodeDefaults = true
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(HttpTimeout) {
            socketTimeoutMillis = 20000
            requestTimeoutMillis = 20000
            connectTimeoutMillis = 20000
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
            level = LogLevel.ALL

        }
        HttpResponseValidator {
            handleResponseExceptionWithRequest { exception, request ->
                val clientException = exception as? ClientRequestException
                    ?: return@handleResponseExceptionWithRequest
                throw clientException
            }
        }
        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }
}

suspend inline fun <reified T : Any> ApiClient.makeRequest(
    urlBuilder: URLBuilder,
    methodType: HttpMethod = HttpMethod.Get,
    toAppendHeaders: List<Pair<String, String>> = emptyList(),
    noinline body: (HttpRequestBuilder.() -> Unit)? = null
): Either<Failure.NetworkFailure, T> {
    return try {
        Either.Right(
            this.client.request(
                url = urlBuilder.build()
            ) {
                method = methodType
                headers.append("Authorization", "Bearer ${authSettings.getAccessToken().first()}")

                toAppendHeaders.forEach { (key, value) ->
                    headers.append(key, value)
                }
                body?.let { it() }
            }.body()
        )
    } catch (e: RedirectResponseException) { // for 3xx
        val localizedMessage = kotlin.runCatching {
            e.response.body<ErrorDto>().error
        }.getOrNull()

        Either.Left(Failure.NetworkFailure.RedirectException(e, localizedMessage))
    } catch (e: ClientRequestException) { // for 4 xx
        val localizedMessage = kotlin.runCatching {
            e.response.body<ErrorDto>().error?: e.response.body<ErrorDto>().message
        }.getOrNull()

        Either.Left(Failure.NetworkFailure.ClientException(e, localizedMessage))
    } catch (e: ServerResponseException) { // for 5xx

        Either.Left(Failure.NetworkFailure.ServerException(e))
    } catch (e: Exception) {
        Either.Left(Failure.NetworkFailure.UnknownException(e))
    }
}

sealed interface Failure {
    fun getErrorMessage(): String?

    object Unknown : Failure {
        override fun getErrorMessage(): String? {
            return "Unknown Error!"
        }
    }

    sealed class NetworkFailure(val exception: Exception) : Failure {

        override fun getErrorMessage(): String? {
            return exception.message
        }

        class RedirectException(
            exception: RedirectResponseException,
            private val localizedMessage: String? = null
        ) : NetworkFailure(exception) {

            override fun getErrorMessage(): String? {
                return localizedMessage ?: exception.message
            }
        }

        class ClientException(
            exception: ClientRequestException,
            private val localizedMessage: String? = null
        ) :
            NetworkFailure(exception) {

            override fun getErrorMessage(): String? {
                return localizedMessage ?: exception.message
            }
        }

        class ServerException(exception: ServerResponseException) : NetworkFailure(exception)

        class UnknownException(exception: Exception) :
            NetworkFailure(exception)
    }

    sealed interface DatabaseFailure : Failure {

        override fun getErrorMessage(): String? {
            return "Database Error!"
        }

        sealed interface FindFailure : DatabaseFailure {
            object ItemNotFoundInDb : FindFailure
        }

        sealed interface InsertFailure : DatabaseFailure {
            object ItemNotInserted : InsertFailure
        }

        sealed interface ReadFailure : DatabaseFailure {
            object EmptyList : ReadFailure
        }
    }

    sealed interface SettingsFailure : Failure {
        override fun getErrorMessage(): String? {
            return "Settings Error!"
        }

        class SettingsNotFound<T>(val initialData: T? = null) : SettingsFailure
    }

}

@kotlinx.serialization.Serializable
data class ErrorDto(
    @SerialName("error") val error: String?=null,
    @SerialName("Message") val message: String?=null,
    @SerialName("Title") val title: String?=null,
)