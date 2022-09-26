package com.serhii.stasiuk.buttontoaction.utils

import android.util.Log
import com.serhii.stasiuk.buttontoaction.BuildConfig

object Logger {

    private val isLoggingEnabled = BuildConfig.DEBUG

    @JvmStatic
    fun d(tag: String?, msg: String?) {
        if (isLoggingEnabled) Log.d(tag?.extendTag(), msg ?: "")
    }

    @JvmStatic
    fun d(tag: String?, msg: String?, e: Exception? = null) {
        if (isLoggingEnabled) Log.d(tag?.extendTag(), msg ?: "", e)
    }

    @JvmStatic
    fun i(tag: String, msg: String) {
        if (isLoggingEnabled) Log.i(tag.extendTag(), msg)
    }

    @JvmStatic
    fun e(tag: String?, msg: String, e: Exception? = null) {
        if (isLoggingEnabled) Log.e(tag?.extendTag(), msg, e)
    }

    @JvmStatic
    fun v(tag: String, msg: String) {
        if (isLoggingEnabled) Log.v(tag.extendTag(), msg)
    }

    @JvmStatic
    fun w(tag: String, msg: String) {
        if (isLoggingEnabled) Log.w(tag.extendTag(), msg)
    }

    @JvmStatic
    fun w(tag: String, msg: String, e: Exception? = null) {
        if (isLoggingEnabled) Log.w(tag.extendTag(), msg, e)
    }

    @JvmStatic
    fun wtf(tag: String, msg: String) {
        if (isLoggingEnabled) Log.wtf(tag.extendTag(), msg)
    }

    private fun String.extendTag(): String {
        return "LOG_$this"
    }
}