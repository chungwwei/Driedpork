package com.example.driedpork

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.driedpork.ui.theme.DriedporkTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                        MessageList(messages = listOf("1", "3", "5"))

                    }

                }
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
fun MessageList(messages: List<String>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .padding(15.dp)
    ) {
        messages.forEach {
            message -> CryptoItem(message)
        }

    }
}


@Composable
fun CryptoItem(message: String) {
    Card(
        modifier = Modifier
            .border(width = 1.dp, color = Color.Blue ,shape = RoundedCornerShape(24.dp))
            .padding(16.dp)
            .background(color = Color.Black)
    ) {
        Row () {
            // crypto icon
            Text("hodler")
//            Image(
//                painter = painterResource(id = R.drawable.ic_movie),
//                contentDescription = "temp hodler",
//                modifier = Modifier.size(24.dp),
//            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // crypto full name and short hand column
                Column() {
                    Text("Bitcoin")
                    Text("BTC")
                }

                // price and percentage change
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text("$41,340.73")
                    Text("+3.00%")
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