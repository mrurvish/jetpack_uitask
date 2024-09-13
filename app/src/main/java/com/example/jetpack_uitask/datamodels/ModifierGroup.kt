package com.example.jetpack_uitask.datamodels

import com.google.gson.annotations.SerializedName

data class ModifierGroup(
    @SerializedName("_id") val id: String? = null,
    @SerializedName("name") val name: List<String>? = null,
    @SerializedName("sequence_number") val sequenceNumber: Int? = null,
    @SerializedName("unique_id") val uniqueId: Int? = null,
    @SerializedName("list") val list: List<ModifierItem>? = null,
    @SerializedName("max_range") val maxRange: Int? = null,
    @SerializedName("range") val range: Int? = null,
    @SerializedName("type") val type: Int? = null,
    @SerializedName("is_required") val isRequired: Boolean? = null,
    @SerializedName("modifierId") val modifierId: String? = null,
    @SerializedName("modifierGroupId") val modifierGroupId: String? = null,
    @SerializedName("modifierGroupName") val modifierGroupName: String? = null,
    @SerializedName("modifierName") val modifierName: String? = null,
    @SerializedName("isAssociated") val isAssociated: Boolean? = null,
    @SerializedName("user_can_add_specification_quantity") val userCanAddSpecificationQuantity: Boolean? = null
)
