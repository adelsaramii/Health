package core.auth.data.remote

import core.main.profile.data.remote.dto.UserGender
import kotlinx.serialization.Serializable

@Serializable
data class UserDtoOut(
    val name: String,
    val nationalCode: String,
    val age: Int,
    val gender: UserGender,
    val height: Int,
    val address: String,
    val password: String,
)