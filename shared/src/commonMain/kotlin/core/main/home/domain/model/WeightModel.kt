package core.main.home.domain.model

@kotlinx.serialization.Serializable
data class WeightModel(
    var deviceId: Int,
    var date: String,
    var value: Int
)