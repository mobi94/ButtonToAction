package com.serhii.stasiuk.buttontoaction.data.repository

import com.serhii.stasiuk.buttontoaction.data.datasource.local.Prefs
import com.serhii.stasiuk.buttontoaction.domain.entity.ButtonActionType
import com.serhii.stasiuk.buttontoaction.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val prefs: Prefs
) : SettingsRepository {
    override fun saveButtonClickTime(type: ButtonActionType, millis: Long) {
        prefs.saveButtonClickTime(type, millis)
    }

    override fun getButtonClickTime(type: ButtonActionType) = prefs.getButtonClickTime(type)
}