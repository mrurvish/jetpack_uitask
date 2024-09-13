package com.example.jetpack_uitask.ui.stat

import com.example.jetpack_uitask.datamodels.CartItem

data class CheckoutStat(val test: String = "", val itemlist: List<CartItem> = listOf(), var isUpdate:Boolean= true,var totel: Int = 0)

sealed class CheckoutUiEvents() {

}