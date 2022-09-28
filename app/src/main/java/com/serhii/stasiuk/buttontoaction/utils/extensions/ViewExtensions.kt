package com.serhii.stasiuk.buttontoaction.utils.extensions

import android.content.res.Resources
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator


val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
val Int.sp: Int
    get() = (this * Resources.getSystem().displayMetrics.scaledDensity).toInt()
val Int.pt: Float
    get() = (this * Resources.getSystem().displayMetrics.density)

fun View.animateRotation(
    degree: Float,
    duration: Long = 700,
    shouldReset: Boolean = true
) {
    if (shouldReset) rotation = 0f
    animate().rotation(degree).setDuration(duration)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .start()
}