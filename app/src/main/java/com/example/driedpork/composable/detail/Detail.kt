package com.example.driedpork.composable.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.driedpork.screen.detail.DetailScreenUiState
import com.example.driedpork.screen.detail.DetailScreenViewModel
import com.patrykandpatryk.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatryk.vico.compose.axis.vertical.startAxis
import com.patrykandpatryk.vico.compose.chart.Chart
import com.patrykandpatryk.vico.compose.chart.line.lineChart
import com.patrykandpatryk.vico.compose.chart.scroll.rememberChartScrollSpec
import com.patrykandpatryk.vico.core.axis.vertical.VerticalAxis
import com.patrykandpatryk.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatryk.vico.core.component.text.textComponent
import com.patrykandpatryk.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatryk.vico.core.entry.FloatEntry
import android.graphics.Color as AndroidColor

@Composable
fun RowInfoItem(
    left: String,
    right: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = left)
        Text(text = right)
    }
}

@Composable
fun InfoColumn(uiState: DetailScreenUiState) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = 8.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            RowInfoItem(left = "Market Cap Rank", right = uiState.marketCapRank)
            RowInfoItem(left = "Market Cap", right = uiState.marketCap)
//        RowInfoItem(left = "Fully Diluted Valuation", right = )
            RowInfoItem(left = "Trading Volume", right = uiState.totalVolume)
            RowInfoItem(left = "24H high", right = uiState.high24h)
            RowInfoItem(left = "24H Low", right = uiState.low24h)
            RowInfoItem(left = "Circulating Supply", right = uiState.circulatingSupply)
            RowInfoItem(left = "Total Supply", right = uiState.totalSupply)
            RowInfoItem(left = "ATH", right = uiState.ath)
            RowInfoItem(left = "ATL", right = uiState.atl)
        }
    }
}

@Composable
fun DetailScreen(detailScreenViewModel: DetailScreenViewModel) {
    val uiState by detailScreenViewModel.uiState.collectAsState()
    val producer = ChartEntryModelProducer(toFloatEntry(uiState.prices))

    val textComponent = textComponent {
        textSizeSp = 18f
        color = AndroidColor.MAGENTA
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (uiState.image.isNotEmpty()) {
                Image(
                    painter = rememberImagePainter(uiState.image),
                    contentDescription = "Crypto Icon",
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.CenterVertically)
                )
            }
            Text(uiState.currentPrice, style = MaterialTheme.typography.h4)
        }
        Chart(
            chartScrollSpec = rememberChartScrollSpec(isScrollEnabled = false),
            chart = lineChart(
                axisValuesOverrider = AxisValuesOverrider.adaptiveYValues(1.0f),
            ),
            chartModelProducer = producer,
            startAxis = startAxis(
                label = textComponent,
                horizontalLabelPosition = VerticalAxis.HorizontalLabelPosition.Inside,
                maxLabelCount = 5,
                guideline = null,
            ),
            bottomAxis = bottomAxis(
                tickLength = 0.dp,
                label = null,
                guideline = null,
            ),
        )
        DaysSelection(
            daysSelected = uiState.daysSelected, detailScreenViewModel = detailScreenViewModel
        )
        InfoColumn(uiState = uiState)
    }
}

fun toFloatEntry(prices: List<List<Double>>): List<FloatEntry> {
    return prices.mapIndexed() { index, list ->
        FloatEntry(index.toFloat(), list[1].toFloat())
    }
}

@Composable
private fun ItemDay(
    day: String, isSelected: Boolean, onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(24.dp),
        contentColor = if (isSelected) Color.LightGray else Color.Transparent,
    ) {
        Text(
            text = day,
        )
    }
}


@Composable
private fun DaysSelection(
    daysSelected: String,
    detailScreenViewModel: DetailScreenViewModel,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 24.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = 8.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ItemDay(day = "1D", isSelected = daysSelected == "1D") {
                detailScreenViewModel.onDaysSelect("1D")
            }
            ItemDay(day = "1W", isSelected = daysSelected == "1W") {
                detailScreenViewModel.onDaysSelect("1W")
            }
            ItemDay(day = "1M", isSelected = daysSelected == "1M") {
                detailScreenViewModel.onDaysSelect("1M")
            }
            ItemDay(day = "3M", isSelected = daysSelected == "3M") {
                detailScreenViewModel.onDaysSelect("3M")
            }
            ItemDay(day = "max", isSelected = daysSelected == "max") {
                detailScreenViewModel.onDaysSelect("max")
            }
        }
    }
}