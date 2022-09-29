package com.serhii.stasiuk.buttontoaction.domain.repository

import com.serhii.stasiuk.buttontoaction.domain.entity.ButtonActionType

interface SettingsRepository {
    fun saveButtonClickTime(type: ButtonActionType, millis: Long)
    fun getButtonClickTime(type: ButtonActionType): Long?
}