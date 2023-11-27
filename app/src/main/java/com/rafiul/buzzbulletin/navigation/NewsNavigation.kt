package com.rafiul.buzzbulletin.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rafiul.buzzbulletin.screens.details.DetailsScreen
import com.rafiul.buzzbulletin.screens.home.HomeScreen
import com.rafiul.buzzbulletin.screens.home.HomeViewModel

@Composable
fun NewsNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NewsScreens.HomeScreen.name
    ) {

        composable(route = NewsScreens.HomeScreen.name) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(navController = navController,viewmodel = homeViewModel)
        }

        composable(route = NewsScreens.DetailsScreen.name) {
            DetailsScreen(navController = navController)
        }

    }
}