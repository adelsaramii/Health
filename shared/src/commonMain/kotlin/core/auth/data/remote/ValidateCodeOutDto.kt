package core.auth.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ValidateCodeOutDto(
    val number: String,
    val code: String
)