package com.example.jetpack_uitask.datamodels

object Cart {
    var item: Item? = null
    var cartItem: CartItem = CartItem()
    val itemslist: MutableList<CartItem> = mutableListOf()
}