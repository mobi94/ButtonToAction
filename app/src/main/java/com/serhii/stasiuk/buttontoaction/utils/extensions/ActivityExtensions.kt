package com.serhii.stasiuk.buttontoaction.utils.extensions

import android.app.Activity
import android.widget.Toast

fun Activity.showToast(message: String): Toast {
    return Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).also {
        it.show()
    }
}