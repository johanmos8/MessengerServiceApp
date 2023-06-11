package com.example.presentation.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.ContactsContract
import android.telephony.SubscriptionManager
import androidx.core.app.ActivityCompat
import java.io.IOException

object ContactHelper {

    @SuppressLint("Range")
    fun getContactPhoneNumber(contactUri: Uri, context: Context): String? {
        val contentResolver = context.contentResolver
        val projection = arrayOf(ContactsContract.Contacts.HAS_PHONE_NUMBER)
        contentResolver.query(contactUri, projection, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val hasPhoneNumber =
                    cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                if (hasPhoneNumber > 0) {

                    val phoneCursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                                ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
                        arrayOf(contactUri.lastPathSegment),
                        null
                    )
                    phoneCursor?.use { phoneCursor ->
                        if (phoneCursor.moveToFirst()) {
                            val phoneNumberIndex =
                                phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            if (phoneNumberIndex >= 0) {
                                return phoneCursor.getString(phoneNumberIndex)
                            }
                        }
                    }
                }
            }
        }

        return null
    }

    @SuppressLint("Range")
    fun getContactPhoto(contactUri: Uri, context: Context): Uri? {
        val contentResolver = context.contentResolver

        val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)
        contentResolver.query(contactUri, projection, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val photoUriString =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
                if (photoUriString != null) {
                    return Uri.parse(photoUriString)
                   /* return try {
                        val inputStream = contentResolver.openInputStream(photoUri)
                        BitmapFactory.decodeStream(inputStream)
                    } catch (e: IOException) {
                        e.printStackTrace()
                        null
                    }*/
                }
            }
        }

        return null
    }

    fun getContactName(contactUri: Uri, context: Context): String? {
        val contentResolver = context.contentResolver
        val projection = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)

        val phoneNumber = ContactHelper.getContactPhoneNumber(contactUri, context)
        contentResolver.query(contactUri, projection, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                return cursor.getString(nameIndex)
            }
        }
        return null
    }

    /**
     * function that get owner's phone number. to initiate a new chat
     */


}
