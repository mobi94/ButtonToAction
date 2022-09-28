package com.serhii.stasiuk.buttontoaction.utils.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat


fun Context.getColorRes(@ColorRes colorResourceId: Int) =
    ContextCompat.getColor(this, colorResourceId)

fun Context.getDrawableRes(@DrawableRes drawableResourceId: Int) =
    ContextCompat.getDrawable(this, drawableResourceId)