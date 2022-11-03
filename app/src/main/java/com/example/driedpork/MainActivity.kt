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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Info
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import coil.compose.rememberImagePainter
import com.example.driedpork.coingecko.CoingeckoAPI
import com.example.driedpork.model.coingecko.Market
import com.example.driedpork.ui.theme.DriedporkTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("MainActivity", "onCreate")
        lifecycleScope.launchWhenCreated {
            Log.i("MainActivity", "launchWhenCreated")
            try {
                val ping = CoingeckoAPI.retrofitService.ping()
                Log.i("MainActivity", "ping: $ping")
                println(ping)
                val marketResponse = CoingeckoAPI.retrofitService.getCoinsMarkets(
                    vs_currency = "usd",
                    ids = null,
                    order = "market_cap_desc",
                    per_page = 100,
                    page = 1,
                    sparkline = false,
                    price_change_percentage = "1h,24h,7d,14d,30d,200d,1y")
                val coins = marketResponse.body();
                Log.i("MainActivity", "coins: $coins")

                setContent {
                    DriedporkTheme {
                        // A surface container using the 'background' color from the theme
                        //                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                        //
                        //                    ColumnThings()
                        //                }
                        Scaffold(
                            topBar = { TopBar() },
                            bottomBar = { BottomNavigationBar() }

                        ) {

                            Box() {
                                CoinsList(coins = coins!!)
                            }
                        }
                    }
                }
                Log.i("MainActivity", "coins: ${coins}")
            } catch (e: Exception) {
                Log.e("MainActivity", "Exception: $e")
            }
        }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DriedporkTheme {
        Greeting("Android")
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
//        messages.forEach {
//            message -> CryptoItem(message)
//        }
//    }
}


@Composable
fun CryptoItem(m: Market) {
    val percentageChangeColor = if (m.price_change_percentage_24h!! > 0) Color.Green else Color.Red
    Box(
        modifier = Modifier
            .border(width = 1.dp, color = Color.Blue, shape = RoundedCornerShape(24.dp))
            .padding(16.dp)
    ) {
        Row () {
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
fun BottomNavigationBar() {
    val items = listOf(
        "home",
        "favorite",
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

@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar()
}