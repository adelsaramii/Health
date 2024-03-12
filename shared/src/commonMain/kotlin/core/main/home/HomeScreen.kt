package core.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.barChart.BarChart
import com.aay.compose.barChart.model.BarParameters
import com.aay.compose.baseComponents.model.LegendPosition
import com.aay.compose.donutChart.model.ChartTypes
import core.auth.presentation.AuthViewModel
import core.main.home.domain.model.BloodOxygenModel
import core.main.home.domain.model.BloodPressureModel
import core.main.home.domain.model.BodyTemperatureModel
import core.main.home.domain.model.WeightModel
import data.base.Loaded
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import util.component.NeverOverScroll
import util.helper.state
import util.theme.primary
import util.theme.secondary
import util.theme.textColor
import util.theme.white

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = koinInject()) {

    val state by viewModel.state()

    val loading = remember { mutableStateOf(true) }

    val healthChartInfoType = remember {
        mutableStateOf(HealthInfoTypeEnum.BloodPressure)
    }

    LaunchedEffect(state.bloodPressureList) {
        if (state.bloodPressureList is Loaded) {
            loading.value = false
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier.fillMaxWidth().clip(
                RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp)
            ).background(primary).padding(start = 16.dp, end = 16.dp, top = 36.dp, bottom = 24.dp)
        ) {

            Column {

                Text(
                    text = "سلام عادل",
                    style = MaterialTheme.typography.h5,
                    color = white,
                    fontWeight = FontWeight.Medium
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "هوای خوبیه برای دوییدن نه؟", color = white
                )

            }

            Image(
                modifier = Modifier.size(40.dp).align(Alignment.TopEnd),
                painter = painterResource("icon_smile.png"),
                contentDescription = null
            )
        }

        Row(
            Modifier.fillMaxWidth().padding(16.dp).clip(RoundedCornerShape(16.dp))
                .background(Color.LightGray).padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HealthInfoHeaderItem(
                "فشار خون",
                healthChartInfoType.value == HealthInfoTypeEnum.BloodPressure
            ) {
                healthChartInfoType.value = HealthInfoTypeEnum.BloodPressure
            }
            HealthInfoHeaderItem(
                "اکسیژن خون",
                healthChartInfoType.value == HealthInfoTypeEnum.BloodOxygen
            ) {
                healthChartInfoType.value = HealthInfoTypeEnum.BloodOxygen
            }
            HealthInfoHeaderItem(
                "وزن",
                healthChartInfoType.value == HealthInfoTypeEnum.Weight
            ) {
                healthChartInfoType.value = HealthInfoTypeEnum.Weight
            }
            HealthInfoHeaderItem(
                "دمای بدن",
                healthChartInfoType.value == HealthInfoTypeEnum.BodyTemperature
            ) {
                healthChartInfoType.value = HealthInfoTypeEnum.BodyTemperature
            }
        }

        if (loading.value) {

            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

        } else {

            when (healthChartInfoType.value) {
                HealthInfoTypeEnum.BloodOxygen -> {
                    HealthChart(
                        chartData = healthInfoDataToChart4(state.bloodOxygenList.data),
                        xAxisData = state.bloodOxygenList.data?.map { it.date } ?: listOf()
                    )
                }

                HealthInfoTypeEnum.BloodPressure -> {
                    HealthChart(
                        chartData = healthInfoDataToChart3(state.bloodPressureList.data),
                        xAxisData = state.bloodPressureList.data?.map { it.date } ?: listOf()
                    )
                }

                HealthInfoTypeEnum.BodyTemperature -> {
                    HealthChart(
                        chartData = healthInfoDataToChart2(state.bodyTemperatureList.data),
                        xAxisData = state.bodyTemperatureList.data?.map { it.date } ?: listOf()
                    )
                }

                HealthInfoTypeEnum.Weight -> {
                    HealthChart(
                        chartData = healthInfoDataToChart1(state.weightList.data),
                        xAxisData = state.weightList.data?.map { it.date } ?: listOf()
                    )
                }
            }

        }

    }

}

@Composable
fun HealthChart(chartData: List<BarParameters>, xAxisData: List<String>) {

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

        NeverOverScroll {

            Box(
                Modifier.padding(bottom = 24.dp, start = 16.dp).fillMaxSize()
            ) {
                BarChart(
                    chartParameters = chartData,
                    spaceBetweenBars = 0.dp,
                    spaceBetweenGroups = 8.dp,
                    gridColor = Color.DarkGray,
                    xAxisData = xAxisData,
                    isShowGrid = true,
                    animateChart = true,
                    showGridWithSpacer = true,
                    yAxisStyle = TextStyle(
                        fontSize = 12.sp,
                        color = Color.DarkGray,
                    ),
                    xAxisStyle = TextStyle(
                        fontSize = 12.sp, color = Color.DarkGray
                    ),
                    yAxisRange = 5,
                    legendPosition = LegendPosition.DISAPPEAR,
                    barCornerRadius = 8.dp,
                    barWidth = 60.dp
                )
            }

        }

    }

}

@Composable
fun HealthInfoHeaderItem(title: String, isSelected: Boolean, onClick: () -> Unit) {
    Text(
        title,
        color = if (isSelected) white else textColor,
        fontSize = 13.sp,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) primary else Color.Transparent)
            .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp)
            .clickable {
                onClick()
            }
    )
}

fun healthInfoDataToChart4(data: List<BloodOxygenModel>?): List<BarParameters> {
    val list = arrayListOf<Double>()
    data?.map { list.add(it.value.toDouble()) }

    return listOf(BarParameters("", list, secondary))
}

fun healthInfoDataToChart3(data: List<BloodPressureModel>?): List<BarParameters> {
    val list = arrayListOf<Double>()
    data?.map { list.add(it.value.toDouble()) }

    return listOf(BarParameters("", list, secondary))
}

fun healthInfoDataToChart2(data: List<BodyTemperatureModel>?): List<BarParameters> {
    val list = arrayListOf<Double>()
    data?.map { list.add(it.value.toDouble()) }

    return listOf(BarParameters("", list, secondary))
}

fun healthInfoDataToChart1(data: List<WeightModel>?): List<BarParameters> {
    val list = arrayListOf<Double>()
    data?.map { list.add(it.value.toDouble()) }

    return listOf(BarParameters("", list, secondary))
}