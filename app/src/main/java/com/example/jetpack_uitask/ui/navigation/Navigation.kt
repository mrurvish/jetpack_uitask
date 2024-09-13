package com.example.jetpack_uitask.ui.navigation

import androidx.navigation.NavController
import com.example.jetpack_uitask.datamodels.Screen

fun NavController.navigateToItemScreen(){
    navigate(Screen.ITEM)
}

fun NavController.navigateToModifierScreen(){
    navigate(Screen.MODIFIER)
}

fun NavController.navigateToCheckoutScreen(){
    navigate(Screen.CHECKOUT)
}