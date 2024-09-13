package com.example.jetpack_uitask.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.jetpack_uitask.datamodels.Cart
import com.example.jetpack_uitask.ui.stat.CheckoutStat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(): ViewModel() {
    private val uiState = MutableStateFlow(CheckoutStat())
    fun consumableState() = uiState.asStateFlow()
    init {
        uiState.update {
            it.copy(
                itemlist = Cart.itemslist,
                isUpdate = !it.isUpdate
            )
        }
    }
    fun addClick(index: Int) {
        val listItem = Cart.itemslist[index]
        listItem.qty += 1
        Cart.itemslist[index] = listItem
        uiState.update {
          it.copy(
              itemlist = Cart.itemslist.toList(),
              isUpdate = !it.isUpdate
          )
        }
    }

    fun removeClick(index: Int) {
        val listItem = Cart.itemslist[index]
        if (listItem.qty > 1) {
            listItem.qty -= 1
            Cart.itemslist[index] = listItem
        }else{
            Cart.itemslist.removeAt(index)
        }
        uiState.update {
            it.copy(
                itemlist = Cart.itemslist.toList(),
                isUpdate = !it.isUpdate
            )
        }
    }

    fun cancelClick(index: Int) {
        Cart.itemslist.removeAt(index)
        uiState.update {
            it.copy(
                itemlist = Cart.itemslist.toList(),
                isUpdate = !it.isUpdate
            )
        }
        Log.d("checkout",uiState.value.itemlist.toString())
    }

}