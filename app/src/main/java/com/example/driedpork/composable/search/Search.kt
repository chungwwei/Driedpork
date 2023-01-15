package com.example.driedpork.composable.search

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.driedpork.composable.LoadingSpinner
import com.example.driedpork.screen.search.CoinDisplay
import com.example.driedpork.screen.search.SearchScreenViewModel

@Composable
fun SearchResults(label: String, coins: List<CoinDisplay>, onItemClick: (coinId: String) -> Unit) {
    Column() {
        Box() {
            Text(label)
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            items(coins.size) { index ->
                SearchResultItem(coins[index], onItemClick)
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchResultItem(coin: CoinDisplay, onItemClick: (coinId: String) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = 8.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .combinedClickable(
                    onClick = { onItemClick(coin.id) },
                )
                .padding(16.dp),
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
}


@Composable
fun SearchTextField(
    queryText: String, onQueryTextChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .border(width = 1.dp, color = Color.Blue, shape = RoundedCornerShape(24.dp))
    ) {
        TextField(
            singleLine = true,
            value = queryText,
            onValueChange = onQueryTextChange,
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
}


@Composable
fun SearchScreen(
    searchScreenViewModel: SearchScreenViewModel, onItemClick: (coinId: String) -> Unit
) {
    val queryText = remember { mutableStateOf("") }
    val uiState by searchScreenViewModel.uiState.collectAsState()
    val coins = uiState.trendingCoinList
    val firstFiveCoins = uiState.firstFiveCoins
    val isLoading = uiState.isLoading
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp)
    ) {
        SearchTextField(queryText = queryText.value, onQueryTextChange = {
            queryText.value = it
            searchScreenViewModel.search(it)
        })
        if (queryText.value.isNotEmpty()) {
            if (isLoading) {
                LoadingSpinner()
            } else {
                SearchResults("Search", firstFiveCoins, onItemClick)
            }
        } else {
            SearchResults("Trending", coins.take(3), onItemClick)
        }
    }
}