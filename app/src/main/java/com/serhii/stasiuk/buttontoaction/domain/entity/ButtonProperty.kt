package com.serhii.stasiuk.buttontoaction.domain.entity

import com.google.gson.annotations.SerializedName

data class ButtonProperty(
    val type: ButtonActionType,
    @SerializedName("enabled")
    val isEnabled: Boolean,
    val priority: Int,
    @SerializedName("valid_days")
    val validDays: List<Int>,
    @SerializedName("cool_down")
    val coolDownMillis: Long
)