package com.example.jetpack_uitask.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.jetpack_uitask.datamodels.Cart
import com.example.jetpack_uitask.ui.stat.ItemStat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(): ViewModel() {
    private val uiState = MutableStateFlow(ItemStat())
    fun consumableState() = uiState.asStateFlow()
    fun checkCart(){
        var size = 0
        Cart.itemslist.forEach {
            size += it.qty
        }
        uiState.update {
            it.copy(
                totalCartItem = Cart.itemslist.size
            )
        }
        if (Cart.itemslist.isNullOrEmpty())
        {

            uiState.update {
                it.copy(
                    isAddRemoveButton = false,
                    itemCount = size
                )
            }
        }else{

            uiState.update {
                it.copy(
                    isAddRemoveButton = true,
                    itemCount = size
                )
            }
        }
    }
}