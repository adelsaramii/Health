package core.main.home.data.remote.dto

import core.main.home.domain.model.BloodOxygenModel
import core.main.home.domain.model.BloodPressureModel
import core.main.home.domain.model.BodyTemperatureModel
import core.main.home.domain.model.WeightModel

@kotlinx.serialization.Serializable
data class HealthInfoBulkDto(
    var weightList: List<WeightModel> = listOf(),
    var bloodOxygenList: List<BloodOxygenModel> = listOf(),
    var bloodPressureList: List<BloodPressureModel> = listOf(),
    var bodyTemperatureList: List<BodyTemperatureModel> = listOf(),
)