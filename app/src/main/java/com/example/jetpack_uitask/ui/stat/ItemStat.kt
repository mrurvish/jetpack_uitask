package com.example.jetpack_uitask.ui.stat

data class ItemStat(val isAddRemoveButton: Boolean = false,val itemCount: Int = 0,val totalCartItem: Int = 0)

sealed class ItemUiEvents() {

}