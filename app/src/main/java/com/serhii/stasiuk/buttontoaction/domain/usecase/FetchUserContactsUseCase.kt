package com.serhii.stasiuk.buttontoaction.domain.usecase

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import com.serhii.stasiuk.buttontoaction.presentation.model.ContactAdapterItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class FetchUserContactsUseCase(
    private val context: Context
) {
    suspend operator fun invoke(): List<ContactAdapterItem> = withContext(Dispatchers.IO) {
        val contacts = arrayListOf<ContactAdapterItem>()
        context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )?.let { cursor ->
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
                    val displayNameIndex =
                        cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
                    val imageUriIndex = cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI)
                    if (idIndex >= 0 && displayNameIndex >= 0) {
                        val id = cursor.getString(idIndex)
                        val fullName = cursor.getString(displayNameIndex) ?: ""
                        val emailJob = async { parseEmail(id) }
                        val phoneNumberJob = async { parsePhoneNumber(id) }
                        val photoUri = if (imageUriIndex >= 0) {
                            cursor.getString(imageUriIndex)?.let(Uri::parse)
                        }
                        else null
                        if (fullName.isNotBlank()) {
                            contacts.add(
                                ContactAdapterItem(
                                    id = id,
                                    fullName = fullName,
                                    phoneNumber = phoneNumberJob.await(),
                                    email = emailJob.await(),
                                    photoUri = photoUri
                                )
                            )
                        }
                    }
                }
            }
            cursor
        }?.close()
        contacts
    }

    private fun parseEmail(id: String): String {
        var email = ""
        val emailCursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
            arrayOf(id),
            null
        )
        if (emailCursor != null && emailCursor.count > 0) {
            while (emailCursor.moveToNext()) {
                val emailIndex =
                    emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)
                email = if (emailIndex >= 0) emailCursor.getString(emailIndex) ?: "" else ""
            }
        }
        emailCursor?.close()
        return email
    }

    private fun parsePhoneNumber(id: String): String {
        var phoneNumber = ""
        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
            arrayOf(id),
            null
        )
        if (cursor != null && cursor.count > 0) {
            while (cursor.moveToNext()) {
                val phoneIndex =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                phoneNumber = if (phoneIndex >= 0) cursor.getString(phoneIndex) ?: "" else ""
            }
        }
        cursor?.close()
        return phoneNumber
    }
}