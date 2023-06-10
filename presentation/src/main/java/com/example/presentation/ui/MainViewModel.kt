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
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Chat
import com.example.domain.models.UserContact
import com.example.domain.useCases.GetAllChatsUseCase
import com.example.domain.useCases.StartNewChatUseCase
import com.example.presentation.utils.ContactHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Constants
private const val REQUEST_READ_CONTACTS_PERMISSION = 1
private const val PICK_CONTACT_REQUEST = 2

@HiltViewModel
class MainViewModel @Inject constructor(
    private val startNewChatUseCase: StartNewChatUseCase,
    private val getAllChatsUseCase: GetAllChatsUseCase
) : ViewModel() {

    private val _selectedContact = MutableStateFlow<Uri?>(null)
    val selectedContact: StateFlow<Uri?> = _selectedContact

    private val _chatList = MutableStateFlow<List<Chat>>(emptyList())//listOf<Song>())
    val chatList: StateFlow<List<Chat>> = _chatList

    init {
        viewModelScope.launch {
            getAllChatsUseCase.invoke("3123445555").collect { chats ->
                _chatList.value = chats
                Log.d("VM","$chats")
            }

        }
    }

    fun openContacts(pickContactLauncher: ActivityResultLauncher<Intent>) {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        pickContactLauncher.launch(intent)
    }

    fun getContactDetails(contactUri: Uri, context: Context) {

        val phoneNumber = ContactHelper.getContactPhoneNumber(contactUri, context)
        val name = ContactHelper.getContactName(contactUri, context)
        val photo = ContactHelper.getContactPhoto(contactUri, context)

        startNewChat(
            UserContact(
                name = name ?: "Unknown",
                phoneNumber = phoneNumber ?: "",
                profilePicture = photo.toString() ?: ""
            )
        )
    }

    private fun startNewChat(contact: UserContact) {
        viewModelScope.launch {
            val currentUser = getCurrentUser() // Obtener el usuario actual en sesión
            val participants =
                listOf(currentUser, contact) // Lista de participantes en la conversación
            val conversation = Chat(
                participants = participants
            )

            // Iniciar el nuevo chat
            startNewChatUseCase.invoke(conversation)

        }
    }

    private fun getCurrentUser(): UserContact {
        return UserContact(
            name = "Propietario",
            phoneNumber = "3123445555",
            profilePicture = "",
            owner=true
        )
    }

}