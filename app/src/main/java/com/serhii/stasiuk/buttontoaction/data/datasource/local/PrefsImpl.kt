package com.serhii.stasiuk.buttontoaction.data.datasource.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.serhii.stasiuk.buttontoaction.domain.entity.ButtonActionType
import com.serhii.stasiuk.buttontoaction.utils.extensions.fromJson
import com.serhii.stasiuk.buttontoaction.utils.extensions.toJson

class PrefsImpl(
    private val prefs: SharedPreferences,
    private val gson: Gson
) : Prefs {
    override fun saveButtonClickTime(type: ButtonActionType, millis: Long) {
        (getButtonClickMap()?.toMutableMap() ?: mutableMapOf()).let {
            it[type] = millis
            prefs.edit().putString(BUTTON_CLICK_MAP_KEY, it.toJson<Map<ButtonActionType, Long?>>(gson)).apply()
        }
    }

    override fun getButtonClickTime(type: ButtonActionType): Long? {
        return getButtonClickMap()?.get(type)
    }

    private fun getButtonClickMap(): Map<ButtonActionType, Long?>? {
        return prefs.getString(BUTTON_CLICK_MAP_KEY, null)?.let {
            gson.fromJson<Map<ButtonActionType, Long?>>(it)
        }
    }

    private companion object {
        const val BUTTON_CLICK_MAP_KEY = "button_click_map_key"
    }
}