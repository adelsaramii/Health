package core.main.home

import com.attendace.leopard.data.base.BaseViewModel
import core.auth.data.remote.LoginDto
import core.auth.data.remote.SignInFormOutDto
import core.auth.domain.AuthRepository
import core.main.home.domain.HomeRepository
import core.main.home.domain.model.BloodOxygenModel
import core.main.home.domain.model.BloodPressureModel
import core.main.home.domain.model.BodyTemperatureModel
import core.main.home.domain.model.WeightModel
import data.base.Failed
import data.base.LoadableData
import data.base.Loaded
import data.base.Loading
import data.base.NotLoaded
import data.remote.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository,
) : BaseViewModel<HomeViewModel.State>(State()) {

    data class State(
        val bloodOxygenList: LoadableData<List<BloodOxygenModel>> = NotLoaded,
        val bloodPressureList: LoadableData<List<BloodPressureModel>> = NotLoaded,
        val bodyTemperatureList: LoadableData<List<BodyTemperatureModel>> = NotLoaded,
        val weightList: LoadableData<List<WeightModel>> = NotLoaded,
        val snackbarError: Failure? = null
    )

    init {
        healthInfoBulk()
    }

    private fun healthInfoBulk() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.healthInfoBulk().fold(
                ifRight = {
                    updateState {
                        copy(
                            bloodOxygenList = Loaded(it.bloodOxygenList),
                            bloodPressureList = Loaded(it.bloodPressureList),
                            bodyTemperatureList = Loaded(it.bodyTemperatureList),
                            weightList = Loaded(it.weightList),
                        )
                    }
                }, ifLeft = {
                    updateState {
                        copy(
                            snackbarError = it,
                        )
                    }
                }
            )
        }
    }

}