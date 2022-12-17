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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.driedpork.composable.convert.ConvertScreen
import com.example.driedpork.composable.home.HomeScreen
import com.example.driedpork.composable.search.SearchScreen
import com.example.driedpork.screen.convert.ConvertScreenViewModel
import com.example.driedpork.screen.home.HomeScreenViewModel
import com.example.driedpork.screen.search.SearchScreenViewModel

@Composable
fun SetupNavigation(navHostController: NavHostController) {
    val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
    val searchScreenViewModel: SearchScreenViewModel = hiltViewModel()
    val convertScreenViewModel: ConvertScreenViewModel = hiltViewModel()
    NavHost(navController = navHostController, startDestination = "home") {
        composable(route = "home") {
            HomeScreen(homeScreenViewModel = homeScreenViewModel)
        }
        composable(route = "search") {
            SearchScreen(searchScreenViewModel = searchScreenViewModel)
        }
        composable(route = "convert") {
            ConvertScreen(convertScreenViewModel = convertScreenViewModel)
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
    BottomNavigation(
        backgroundColor = Color.DarkGray,
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon (
                        imageVector = item.icon as ImageVector,
                        contentDescription = item.title,
                        tint = if (selectedItem.value == item) Color.White else Color.Gray
                    )
               },
                label = { Text(text = item.title) },
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = selectedItem == item,
                onClick = {
                    /* Add code later */
                    navController.navigate(item.route)
                    selectedItem.value = item
                }
            )
        }
    }
}