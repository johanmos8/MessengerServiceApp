package com.example.presentation.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import com.example.presentation.utils.ContactHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

// Constants
private const val REQUEST_READ_CONTACTS_PERMISSION = 1
private const val PICK_CONTACT_REQUEST = 2

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _selectedContact = MutableStateFlow<Uri?>(null)
    val selectedContact: StateFlow<Uri?> = _selectedContact


    fun openContacts(pickContactLauncher: ActivityResultLauncher<Intent>) {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        pickContactLauncher.launch(intent)
    }

    fun getContactDetails(contactUri: Uri, context: Context) {

        val phoneNumber = ContactHelper.getContactPhoneNumber(contactUri, context)
        val name = ContactHelper.getContactName(contactUri, context)

        Log.d("Contact", "$phoneNumber")
        Log.d("Contact", "$name")
    }


}