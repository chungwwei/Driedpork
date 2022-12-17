package com.example.driedpork.composable.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.driedpork.screen.search.CoinDisplay
import com.example.driedpork.screen.search.SearchScreenViewModel

@Composable
fun SearchResults(label: String, coins: List<CoinDisplay>) {
    Column() {
        Box() {
            Text(label)
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
        ) {
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
            .height(76.dp)
            .padding(horizontal = 8.dp, vertical = 2.dp)
            .border(width = 1.dp, color = Color.Blue, shape = RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
        ) {
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


@Composable
fun SearchScreen(
    searchScreenViewModel: SearchScreenViewModel
) {
    val queryText = remember { mutableStateOf("") }
    val uiState by searchScreenViewModel.uiState.collectAsState()
    val coins = uiState.trendingCoinList
    val firstFiveCoins = uiState.firstFiveCoins
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .border(width = 1.dp, color = Color.Blue, shape = RoundedCornerShape(24.dp))
        ) {
            TextField(
                singleLine = true,
                value = queryText.value,
                onValueChange = {
                    queryText.value = it
                    searchScreenViewModel.search(it)
                },
                label = { Text("Search") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = Color.Blue,
                    textColor = Color.Blue
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
        }
        if (queryText.value.isNotEmpty()) {
            SearchResults("Search", firstFiveCoins)
        } else {
            SearchResults("Trending", coins.take(3))
        }
    }
}