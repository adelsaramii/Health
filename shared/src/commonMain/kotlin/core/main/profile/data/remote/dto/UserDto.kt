package core.main.profile.data.remote.dto

@kotlinx.serialization.Serializable
data class UserDto(
    var id: Int = -1,
    var nationalCode: String = "",
    var name: String = "",
    var number: String = "",
    var age: Int = -1,
    var password: String = "",
    var gender: UserGender = UserGender.None,
    var address: String = "",
    var height: Int = -1,
)