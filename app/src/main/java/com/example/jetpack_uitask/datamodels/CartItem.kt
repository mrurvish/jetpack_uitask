package com.example.jetpack_uitask.datamodels

data class CartItem(
    var price: Int = 0,
    var qty: Int = 1,
    var selscted: List<ModifierItem> = listOf(),

    )