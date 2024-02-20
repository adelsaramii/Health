package core.auth.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class SignInFormOutDto(
    val name: String,
    val nationalCode: String,
    val age: Int,
    val gender: GenderEnum,
    val height: Int,
    val address: String,
    val password: String,
)