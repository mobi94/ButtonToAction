package com.serhii.stasiuk.buttontoaction.utils.extensions

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken


inline fun <reified T> T.toJson(gson: Gson): String = gson.toJson(this)

inline fun <reified T> T.toJson(): String = toJson(Gson())

inline fun <reified T> String.fromJson(): T? = Gson().fromJson(this)

inline fun <reified T> Gson.fromJson(json: String): T? {
    return try {
        fromJson<T>(json, object : TypeToken<T>() {}.type)
    } catch (e: JsonParseException) {
        null
    }
}