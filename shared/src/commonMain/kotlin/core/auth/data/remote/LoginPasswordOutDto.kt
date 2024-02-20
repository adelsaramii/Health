package core.auth.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class LoginPasswordOutDto(
    val nationalCode: String,
    val password: String
)