package com.serhii.stasiuk.buttontoaction.utils.extensions

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.widget.ImageViewCompat
import com.serhii.stasiuk.buttontoaction.presentation.views.LetterTileDrawable


fun getNamedAvatarDrawable(context: Context, name: String, textScale: Float = 0.6f): Drawable {
    return LetterTileDrawable(context)
        .setContactTypeAndColor(LetterTileDrawable.ContactType.PERSON)
        .setScale(textScale)
        .setLetterAndColor(name, name)
        .setIsCircular(true)
}

fun ImageView.setNamedAvatar(name: String, textScale: Float = 0.6f) {
    val drawable = getNamedAvatarDrawable(context, name, textScale)
    setImageDrawable(drawable)
}

fun ImageView.setTint(@ColorRes colorRes: Int) {
    ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(context.getColorRes(colorRes)))
}