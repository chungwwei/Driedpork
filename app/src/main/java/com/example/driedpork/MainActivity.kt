package com.example.driedpork

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.driedpork.coingecko.CoingeckoAPI
import com.example.driedpork.composable.search.SearchResults
import com.example.driedpork.model.coingecko.Market
import com.example.driedpork.model.coingecko.search.SearchResults
import com.example.driedpork.model.coingecko.search.TrendingCoin
import com.example.driedpork.screen.SetupNavigation
import com.example.driedpork.screen.home.HomeRepository
import com.example.driedpork.screen.home.HomeScreenViewModel
import com.example.driedpork.screen.search.CoinDisplay
import com.example.driedpork.screen.search.SearchScreenViewModel
import com.example.driedpork.ui.theme.DriedporkTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreenView()
        }
//        lifecycleScope.launchWhenCreated {
//            try {
//                val marketResponse = CoingeckoAPI.retrofitService.getCoinsMarkets(
//                    vs_currency = "usd",
//                    ids = null,
//                    order = "market_cap_desc",
//                    per_page = 100,
//                    page = 1,
//                    sparkline = false,
//                    price_change_percentage = "1h,24h,7d,14d,30d,200d,1y")
//                val coins = marketResponse.body();
//                Log.i("MainActivity", "coins: $coins")

//                setContent {
//                    DriedporkTheme() {
//                        MainScreenView()
//                    }
//                }
//            } catch (e: Exception) {
//                Log.e("MainActivity", "Exception: $e")
//            }
//        }
    }
}


@Composable
fun MainScreenView() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) }

    ) {
        SetupNavigation(navController)
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
fun CryptoItem(m: Market) {
    val percentageChangeColor = if (m.price_change_percentage_24h > 0) Color.Green else Color.Red
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
                    Text("$${m.current_price}")
                    Text("${m.price_change_percentage_24h}%", color = percentageChangeColor)
                }
            }
        }
    }
}


//sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
//    object Home : NavigationItem("home", R.drawable.ic_home, "Home")
//    object Music : NavigationItem("music", R.drawable.ic_music, "Music")
//    object Movies : NavigationItem("movies", R.drawable.ic_movie, "Movies")
//}

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val items = listOf(
        "home",
        "search",
        "convert",
    )
    BottomNavigation() {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { null },
                label = { Text(text = item) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = false,
                onClick = {
                    /* Add code later */
                    navController.navigate(item)
                }
            )
        }
    }
}

@Composable
fun TopBar() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)

    ) {
        Text("Dried Pork")
        Text("Mott apple drinks")

    }
}

//@Composable
//fun BottomNavigationBarPreview() {
//    BottomNavigationBar()
//}

@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {
    val uiState by homeScreenViewModel.uiState.collectAsState()
    Log.i("HomeScreen", "uiState: $uiState")
    val coins = uiState.coinsList

    Log.i("HomeScreen", "coins: $coins")
    CoinsList(coins = coins)
}

@Composable
fun SearchScreen(
    searchScreenViewModel: SearchScreenViewModel = hiltViewModel()
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


@Composable
fun ConvertScreen() {
    Text("Convert")
}