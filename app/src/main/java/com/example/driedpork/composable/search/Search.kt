package com.example.driedpork.composable.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.driedpork.screen.search.CoinDisplay

@Composable
fun SearchResults(label: String, coins: List<CoinDisplay>) {
    Column() {
        Box() {
            Text(label)
        }
        Column() {
            coins.forEach { coin ->
                SearchResultItem(coin)
            }
        }
    }
}


@Composable
fun SearchResultItem(coin: CoinDisplay) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp, vertical = 2.dp)
            .border(width = 1.dp, color = Color.Blue, shape = RoundedCornerShape(24.dp))
    ) {
        Row() {
            // crypto icon
            Image(
                painter = rememberImagePainter(coin.image),
                contentDescription = "Crypto Icon",
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterVertically)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                // crypto full name and short hand column
                Column() {
                    Text(coin.name)
                    Text(coin.symbol)
                }

                // ranking
                Text("#${coin.marketCapRank}")
            }
        }
    }
}