package com.example.driedpork.composable.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.driedpork.screen.home.HomeScreenViewModel

@Composable
fun CryptoItem(m: Market) {
    val percentageChangeColor = if (m.priceChangePercentage24h > 0) Color.Green else Color.Red
    Box(
        modifier = Modifier
            .border(width = 1.dp, color = Color.Blue, shape = RoundedCornerShape(24.dp))
            .padding(16.dp)
    ) {
        Row() {
            // crypto icon
            Image(
                painter = rememberImagePainter(m.image),
                contentDescription = "Crypto Icon",
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterVertically)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // crypto full name and short hand column
                Column() {
                    Text(m.name)
                    Text(m.symbol)
                }

                // price and percentage change
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text("$${m.currentPrice}")
                    Text("${m.priceChangePercentage24h}%", color = percentageChangeColor)
                }
            }
        }
    }
}


@Composable
fun CoinsList(coins: List<Market>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .padding(15.dp)
    ) {
        items(coins.size) { index ->
            CryptoItem(m = coins[index])
        }
    }
}



@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel
) {
    val uiState by homeScreenViewModel.uiState.collectAsState()
    Log.i("HomeScreen", "uiState: $uiState")
    val coins = uiState.coinsList

    Log.i("HomeScreen", "coins: $coins")
    CoinsList(coins = coins)
}