package com.example.jetpack_uitask.datamodels

import com.google.gson.annotations.SerializedName

data class ModifierItem(
    @SerializedName("_id") val id: String? = null,
    @SerializedName("name") val name: List<String>? = null,
    @SerializedName("price") val price: Int? = null,
    @SerializedName("sequence_number") val sequenceNumber: Int? = null,
    @SerializedName("is_default_selected") val isDefaultSelected: Boolean? = null,
    @SerializedName("specification_group_id") val specificationGroupId: String? = null,
    @SerializedName("unique_id") val uniqueId: Int? = null
)
