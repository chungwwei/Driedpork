package com.example.driedpork.screen

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.driedpork.composable.convert.ConvertScreen
import com.example.driedpork.composable.detail.DetailScreen
import com.example.driedpork.composable.home.HomeScreen
import com.example.driedpork.composable.search.SearchScreen


@Composable
fun SetupNavigation(navHostController: NavHostController) {

    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    val onItemClick: (coin: String) -> Unit = {
//        // put the coin parcelable into the bundle
//        navHostController.currentBackStackEntry?.savedStateHandle?.apply {
//            set("coin", it)
//        }
        navHostController.navigate("detail/${it}")
    }

    NavHost(navController = navHostController, startDestination = "home") {
        composable(route = "home") {
            HomeScreen(
                homeScreenViewModel = hiltViewModel(viewModelStoreOwner), onItemClick = onItemClick
            )
        }
        composable(route = "search") {
            SearchScreen(
                searchScreenViewModel = hiltViewModel(viewModelStoreOwner),
                onItemClick = onItemClick
            )
        }
        composable(route = "convert") {
            ConvertScreen(convertScreenViewModel = hiltViewModel(viewModelStoreOwner))
        }
        composable(
            route = "detail/{coinId}",
            arguments = listOf(navArgument("coinId") { type = NavType.StringType })
        ) {
//            val coin = navHostController.
//                previousBackStackEntry?.
//                savedStateHandle?.
//                get<Market>("coin")

//            val coinId: String = it.arguments?.getString("coinId")!!
            DetailScreen(detailScreenViewModel = hiltViewModel())
        }
    }
}


sealed class NavigationItem(var route: String, var icon: Any, var title: String) {
    object Home : NavigationItem("home", Icons.Filled.Home, "Home")
    object Search : NavigationItem("search", Icons.Filled.Search, "Search")
    object Convert : NavigationItem("convert", Icons.Filled.Refresh, "Convert")
}


@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    // remember selected variable
    var selectedItem = remember { mutableStateOf<NavigationItem>(NavigationItem.Home) }
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Search,
        NavigationItem.Convert,
    )
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination

    BottomNavigation(
        backgroundColor = Color.DarkGray,
    ) {
        items.forEach { item ->
            BottomNavigationItem(icon = {
                Icon(
                    imageVector = item.icon as ImageVector,
                    contentDescription = item.title,
                    tint = if (currentRoute?.route == item.route) Color.White else Color.Gray
                )
            },
                label = { Text(text = item.title) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute?.route == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo("home")
                    }
                    selectedItem.value = item
                })
        }
    }
}