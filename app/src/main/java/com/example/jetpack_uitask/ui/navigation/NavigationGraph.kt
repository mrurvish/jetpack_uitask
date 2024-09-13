package com.example.jetpack_uitask.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpack_uitask.datamodels.Screen
import com.example.jetpack_uitask.ui.screen.Checkout
import com.example.jetpack_uitask.ui.screen.Item
import com.example.jetpack_uitask.ui.screen.Modifier


const val NAVIGATION_HOST_ROUTE = "navigation_host_route"

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.ITEM
    ) {
        composable(Screen.ITEM) {

            Item(
                navigateToModifier = {
                    navController.navigateToModifierScreen()
                },
                navigateToCheckout = {
                    navController.navigateToCheckoutScreen()
                }
            )
        }
        composable(Screen.MODIFIER) {
            Modifier(navigateToCheckout = {
                navController.popBackStack()
            }, onBackPressed = {
                navController.popBackStack()
            })
        }
        composable(Screen.CHECKOUT) {
            Checkout(onBackPressed = { navController.popBackStack() })
        }
    }
}