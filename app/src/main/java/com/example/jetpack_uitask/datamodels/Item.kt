package com.example.jetpack_uitask.datamodels

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("_id") val id: String? = null,
    @SerializedName("name") val name: List<String>? = null,
    @SerializedName("price") val price: Int? =null,
    @SerializedName("item_taxes") val itemTaxes: List<Int>? =null,
    @SerializedName("specifications") val modifierGroups: List<ModifierGroup>?= null
)
