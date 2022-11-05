package com.example.driedpork.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.driedpork.ConvertScreen
import com.example.driedpork.SearchScreen
import com.example.driedpork.HomeScreen

@Composable
fun SetupNavigation(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = "home") {
        composable(route = "home") {
            HomeScreen()
        }
        composable(route = "search") {
            SearchScreen()
        }
        composable(route = "convert") {
            ConvertScreen()
        }
    }
}