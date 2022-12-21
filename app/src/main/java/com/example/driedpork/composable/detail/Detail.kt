package com.example.driedpork.composable.detail

import android.util.Log
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
import com.example.driedpork.model.coingecko.Market
import com.example.driedpork.screen.detail.DetailScreenViewModel
import com.patrykandpatryk.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatryk.vico.compose.axis.vertical.startAxis
import com.patrykandpatryk.vico.compose.chart.Chart
import com.patrykandpatryk.vico.compose.chart.line.lineChart
import com.patrykandpatryk.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatryk.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatryk.vico.core.entry.FloatEntry
import java.text.NumberFormat
import java.util.*

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
fun InfoColumn(coin: Market) {
    val numberFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("en", "US"))
    numberFormat.maximumFractionDigits = 2
    numberFormat.minimumFractionDigits = 2

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))

    ) {
        RowInfoItem(left = "Market Cap Rank", right = coin.marketCapRank.toString())
        RowInfoItem(left = "Market Cap", right = numberFormat.format(coin.marketCap).toString())
        RowInfoItem(left = "Fully Diluted Valuation", right = numberFormat.format(coin.fullyDilutedValuation).toString())
        RowInfoItem(left = "Trading Volume", right = numberFormat.format(coin.totalVolume).toString())
        RowInfoItem(left = "24H high", right = numberFormat.format(coin.high24h).toString())
        RowInfoItem(left = "24H Low", right = numberFormat.format(coin.low24h).toString())
        RowInfoItem(left = "Circulating Supply", right = numberFormat.format(coin.circulatingSupply).toString())
        RowInfoItem(left = "Total Supply", right = numberFormat.format(coin.totalSupply).toString())
    }
}

@Composable
fun DetailScreen(coin: Market, detailScreenViewModel: DetailScreenViewModel) {
    val uiState by detailScreenViewModel.uiState.collectAsState()
    Log.i("HomeScreen", "uiState: $uiState")
    val producer = ChartEntryModelProducer(toFloatEntry(uiState.prices))
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ){
            Image(
                painter = rememberImagePainter(coin.image),
                contentDescription = "Crypto Icon",
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterVertically)
            )
            Text("$${coin.currentPrice}", style = MaterialTheme.typography.h4)
        }
        Chart(
            chart = lineChart(
                axisValuesOverrider = AxisValuesOverrider.adaptiveYValues(1f),
                spacing = 0.05.dp
            ),
            chartModelProducer = producer,
            startAxis = startAxis(
                maxLabelCount = 5,
            ),
            bottomAxis = bottomAxis(),
        )
        InfoColumn(coin = coin)
    }
}

fun toFloatEntry(prices: List<List<Double>>): List<FloatEntry> {
    return prices.mapIndexed() { index, list ->
        FloatEntry(index.toFloat(), list[1].toFloat())
    }
}


//fun getRandomEntries() = List(size = 5) {
//    25f * Random.nextFloat()
//}.mapIndexed { x, y ->
//    FloatEntry(
//        x = x.toFloat(),
//        y = y,
//    )
//}