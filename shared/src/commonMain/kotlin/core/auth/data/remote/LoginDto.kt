package core.auth.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class LoginDto(
    val token: String
)