package com.rafiul.buzzbulletin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rafiul.buzzbulletin.screens.home.HomeScreen

@Composable
fun NewsNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NewsScreens.HomeScreen.name
    ) {

        composable(route = NewsScreens.HomeScreen.name) {
            HomeScreen(navController = navController)
        }

        composable(route = NewsScreens.HomeScreen.name) {
            HomeScreen(navController = navController)
        }

    }
}