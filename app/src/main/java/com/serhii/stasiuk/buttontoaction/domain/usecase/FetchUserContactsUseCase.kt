package com.serhii.stasiuk.buttontoaction.domain.usecase

import android.content.Context
import android.provider.ContactsContract
import com.serhii.stasiuk.buttontoaction.presentation.model.ContactAdapterItem
import kotlinx.coroutines.Dispatchers
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
                        cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                    if (idIndex >= 0 && displayNameIndex >= 0) {
                        val id = cursor.getString(idIndex)
                        val fullName = cursor.getString(displayNameIndex) ?: ""
                        val email = parseEmail(id) ?: ""
                        if (fullName.isNotBlank()) {
                            contacts.add(ContactAdapterItem(id, fullName, email))
                        }
                    }
                }
            }
            cursor
        }?.close()
        contacts
    }

    private fun parseEmail(id: String): String? {
        var email = ""
        val emailCursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
            arrayOf(id), null
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
}