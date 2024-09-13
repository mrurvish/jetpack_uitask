package com.example.jetpack_uitask.ui.stat

import androidx.compose.foundation.layout.PaddingValues
import com.example.jetpack_uitask.datamodels.ModifierGroup
import com.example.jetpack_uitask.datamodels.ModifierItem

data class ModifierStat(
    val test: String = "",
    var requiredModifierGroup: List<ModifierGroup> = listOf(),
    var associateModifierGroup: List<ModifierGroup> = listOf(),

    var hello: String = "",
    var modifierItemList: List<ModifierItem> = listOf(),
    var totel: Int = 0,
)

sealed class ModifierUiEvents() {
    data class OnRadioChange(val modifierItem: ModifierItem, val isrequired: Boolean) : ModifierUiEvents()
    data class OnCheckChange(
        val modifierItem: ModifierItem,
        val selected: Boolean,
        val isrequired: Boolean,
        val isRemove: Boolean
    ) :
        ModifierUiEvents()
}