package com.serhii.stasiuk.buttontoaction.data.datasource.local

import com.serhii.stasiuk.buttontoaction.domain.entity.ButtonActionType

interface Prefs {
    fun saveButtonClickTime(type: ButtonActionType, millis: Long)
    fun getButtonClickTime(type: ButtonActionType): Long?
}