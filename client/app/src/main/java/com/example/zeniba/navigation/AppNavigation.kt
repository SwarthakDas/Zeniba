package com.example.zeniba.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.zeniba.screens.CartScreen
import com.example.zeniba.screens.CatagoryScreen
import com.example.zeniba.screens.HomeScreen
import com.example.zeniba.screens.Login
import com.example.zeniba.screens.SearchScreen
import com.example.zeniba.screens.SignUp
import com.example.zeniba.screens.Splashscreen
import com.example.zeniba.screens.WishlistScreen


@Composable
fun AppNavigation(

) {
    val navController = rememberNavController()

    // Handle navigation based on auth state


    NavHost(
        navController = navController,
        startDestination =  "splash"
    ) {
        composable(Routes.Splash) {
            Splashscreen(navController)
        }
        composable(Routes.Signup) {
            SignUp(navController)
        }
        composable(Routes.Home) {
            HomeScreen(navController)
        }
        composable(Routes.Login) {
            Login(
                onLoggedIn = {
                    // ✅ Prevent back navigation to Login
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Splash) { inclusive = true }
                    }
                }            )
        }
        composable(Routes.Cart) {
            CartScreen(navController)
        }
        composable(Routes.Wishlist) {
            WishlistScreen(navController)
        }
        composable(Routes.Search) {
            SearchScreen(navController)
        }
        composable(Routes.Catagory) {
            CatagoryScreen(navController)
        }

    }
}


