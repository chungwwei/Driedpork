package com.example.driedpork.composable.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.patrykandpatryk.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatryk.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatryk.vico.core.entry.FloatEntry

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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))

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

@Composable
fun DetailScreen(detailScreenViewModel: DetailScreenViewModel) {
    val uiState by detailScreenViewModel.uiState.collectAsState()
    val producer = ChartEntryModelProducer(toFloatEntry(uiState.prices))
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ){
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
            chart = lineChart(
                axisValuesOverrider = AxisValuesOverrider.adaptiveYValues(1f),
                spacing = 0.dp,
            ),
            chartModelProducer = producer,
            startAxis = startAxis(
                maxLabelCount = 5,
                label = null,
            ),
            // bottomAxis with no label
            bottomAxis = bottomAxis(
                label = null,
            ),
        )
        InfoColumn(uiState = uiState)
    }
}

fun toFloatEntry(prices: List<List<Double>>): List<FloatEntry> {
    return prices.mapIndexed() { index, list ->
        FloatEntry(index.toFloat(), list[1].toFloat())
    }
}