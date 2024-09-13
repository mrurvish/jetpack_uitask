package com.example.jetpack_uitask.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.jetpack_uitask.datamodels.Cart
import com.example.jetpack_uitask.datamodels.ModifierGroup
import com.example.jetpack_uitask.datamodels.ModifierItem
import com.example.jetpack_uitask.ui.stat.ModifierStat
import com.example.jetpack_uitask.ui.stat.ModifierUiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ModifierViewModel @Inject constructor() : ViewModel() {
    private val uiState = MutableStateFlow(ModifierStat())
    fun consumableState() = uiState.asStateFlow()


    init {


    }

    fun getRequiredModifierGroup(): List<ModifierGroup> {
        val list: MutableList<ModifierGroup> = mutableListOf()
        Cart.item?.modifierGroups?.forEach {
            if (it.isRequired == true) {
                list.add(it)

            }
        }
        uiState.update {
            it.copy(
                requiredModifierGroup = list
            )
        }

        return list
    }

    fun getAssociateModifierGroup(
        modifierGroupId: String,
        modifierId: String
    ): List<ModifierGroup> {
        val list: MutableList<ModifierGroup> = mutableListOf()
        Cart.item?.modifierGroups?.forEach {
            if (it.isAssociated == true &&
                modifierGroupId.equals(it.modifierGroupId) &&
                modifierId.equals(it.modifierId)
            ) {
                list.add(it)
            }
        }
        return list
    }

    fun onEvent(event: ModifierUiEvents) {
        when (event) {
            is ModifierUiEvents.OnCheckChange -> {

                if (event.selected) {
                    uiState.update {
                        it.copy(
                            modifierItemList = it.modifierItemList + event.modifierItem,
                        )
                    }
                } else {
                    if (event.isRemove) {
                        val list = uiState.value.modifierItemList.toMutableList()
                        list.apply {
                            removeAll {
                                it.id == event.modifierItem.id
                            }
                        }
                        uiState.update {
                            it.copy(
                                modifierItemList = list

                            )
                        }
                    } else {
                        uiState.update {
                            it.copy(
                                modifierItemList = it.modifierItemList - event.modifierItem,

                                )
                        }
                    }
                }
                Log.i("testevent", uiState.value.modifierItemList.toString())
                calculateTolel()
            }

            is ModifierUiEvents.OnRadioChange -> {
                if (event.isrequired) {
                    checkAssociate(event.modifierItem)

                }
                uiState.update {
                    it.copy(
                        modifierItemList = listOf(event.modifierItem),
                    )
                }


                Log.i("testeventradio", uiState.value.modifierItemList.toString())
                calculateTolel()
            }
        }
    }

    private fun checkAssociate(modifierItem: ModifierItem) {

        uiState.update {
            it.copy(
                associateModifierGroup = getAssociateModifierGroup(
                    modifierItem.specificationGroupId.toString(),
                    modifierItem.id.toString()
                )
            )
        }
    }

    fun calculateTolel() {
        uiState.update {
            it.copy(
                totel = it.modifierItemList.sumOf { it.price ?: 0 }
            )
        }
        Log.d("totel", uiState.value.totel.toString())
    }

}