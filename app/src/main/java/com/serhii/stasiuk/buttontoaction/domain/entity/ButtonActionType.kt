package com.serhii.stasiuk.buttontoaction.domain.entity

import com.google.gson.annotations.SerializedName

enum class ButtonActionType {
    @SerializedName("animation")
    ANIMATION,

    @SerializedName("toast")
    TOAST,

    @SerializedName("call")
    CALL,

    @SerializedName("notification")
    NOTIFICATION
}