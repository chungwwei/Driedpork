package com.example.driedpork

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.driedpork.screen.BottomNavigationBar
import com.example.driedpork.screen.SetupNavigation
import dagger.hilt.android.AndroidEntryPoint


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
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
        ) {
            SetupNavigation(navController)
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
        Text("Powered by CoinGecko")

    }
}