package com.serhii.stasiuk.buttontoaction.utils.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.serhii.stasiuk.buttontoaction.utils.Logger


fun Context.getColorRes(@ColorRes colorResourceId: Int) =
    ContextCompat.getColor(this, colorResourceId)

fun Context.getDrawableRes(@DrawableRes drawableResourceId: Int) =
    ContextCompat.getDrawable(this, drawableResourceId)

fun Context.callFromDialer(phoneNumber: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    } catch (e: Exception) {
        Logger.w("Call action", "Error starting call action", e)
    }
}

fun Context.sendEmail(
    address: String,
    subject: String? = null,
    body: String? = null,
) {
    val selectorIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
    }
    val emailIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_EMAIL, arrayOf(address))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
        selector = selectorIntent
    }
    try {
        if (emailIntent.resolveActivity(packageManager) != null) {
            startActivity(emailIntent)
        }
    } catch (e: ActivityNotFoundException) {
        Logger.w("Email action", "There is no email client installed.", e)
    }
}
