package core.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.baseComponents.model.LegendPosition
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import util.component.NeverOverScroll
import util.theme.primary
import util.theme.textColor
import util.theme.white

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomeScreen() {

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
                    text = "هوای خوبیه برای دوییدن نه؟",
                    color = white
                )

            }

            Image(
                modifier = Modifier.size(40.dp).align(Alignment.TopEnd),
                painter = painterResource("icon_smile.png"),
                contentDescription = null
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.LightGray)
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HealthInfoHeaderItem("فشار خون", true)
            HealthInfoHeaderItem("اکسیژن خون", false)
            HealthInfoHeaderItem("وزن", false)
            HealthInfoHeaderItem("دمای بدن", false)
        }

        core.main.home.BarChart()

    }

}

@Composable
fun BarChart() {

    val testLineParameters: List<LineParameters> = listOf(
        LineParameters(
            label = "",
            data = listOf(20.0, 20.0, 80.0, 60.0, 20.0),
            lineColor = Color.Gray,
            lineType = LineType.CURVED_LINE,
            lineShadow = true,
        )
    )

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

        NeverOverScroll {
            Box(
                Modifier.padding(bottom = 24.dp, start = 16.dp, top = 16.dp, end = 16.dp)
                    .fillMaxSize()
            ) {
                LineChart(
                    modifier = Modifier.fillMaxSize(),
                    linesParameters = testLineParameters,
                    isGrid = false,
                    gridColor = Color.Blue,
                    xAxisData = listOf("2015", "2016", "2017", "2018", "2019"),
                    animateChart = true,
                    showGridWithSpacer = true,
                    yAxisStyle = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Gray,
                    ),
                    legendPosition = LegendPosition.DISAPPEAR,
                    xAxisStyle = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.W400
                    ),
                    yAxisRange = 5,
                    oneLineChart = false,
                    gridOrientation = GridOrientation.VERTICAL
                )
            }
        }
    }

//    val testBarParameters: List<BarParameters> = listOf(
//        BarParameters(
//            dataName = "",
//            data = listOf(20.0, 20.0, 80.0, 60.0, 20.0, 99.0, 20.0),
//            barColor = Color(0xFF6C3428)
//        )
//    )
//
//    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
//
//        NeverOverScroll {
//
//            Box(
//                Modifier.padding(bottom = 16.dp, start = 16.dp, top = 16.dp, end = 16.dp)
//                    .fillMaxSize()
//            ) {
//                BarChart(
//                    chartParameters = testBarParameters,
//                    spaceBetweenBars = 0.dp,
//                    spaceBetweenGroups = 16.dp,
//                    gridColor = Color.DarkGray,
//                    xAxisData = listOf(
//                        "شنبه",
//                        "یکشنبه",
//                        "دوشنبه",
//                        "سه شنبه",
//                        "چهارشنبه",
//                        "پنجشنبه",
//                        "جمعه"
//                    ),
//                    isShowGrid = false,
//                    animateChart = true,
//                    showGridWithSpacer = false,
//                    yAxisStyle = TextStyle(
//                        fontSize = 12.sp,
//                        color = Color.DarkGray,
//                    ),
//                    xAxisStyle = TextStyle(
//                        fontSize = 12.sp,
//                        color = Color.DarkGray
//                    ),
//                    yAxisRange = 5,
//                    legendPosition = LegendPosition.DISAPPEAR,
//                    barCornerRadius = 8.dp,
//                    barWidth = 60.dp
//                )
//            }
//
//        }
//
//    }

}

@Composable
fun HealthInfoHeaderItem(title: String, isSelected: Boolean) {
    Text(
        title,
        color = if (isSelected) white else textColor,
        fontSize = 13.sp,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) primary else Color.Transparent)
            .padding(
                start = 12.dp,
                end = 12.dp,
                top = 8.dp,
                bottom = 8.dp
            )
    )
}