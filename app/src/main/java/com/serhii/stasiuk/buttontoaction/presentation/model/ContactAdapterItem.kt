package com.serhii.stasiuk.buttontoaction.presentation.model

import android.net.Uri

data class ContactAdapterItem(
    var id: String,
    val fullName: String,
    val phoneNumber: String,
    val email: String,
    val photoUri: Uri?
)