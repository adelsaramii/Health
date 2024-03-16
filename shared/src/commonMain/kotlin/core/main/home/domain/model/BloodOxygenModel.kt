package core.main.home.domain.model

@kotlinx.serialization.Serializable
data class BloodOxygenModel(
    var deviceId: Int,
    var date: String,
    var value: Int
)