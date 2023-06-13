package com.example.presentation.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Chat
import com.example.domain.models.Message
import com.example.domain.models.UserContact
import com.example.domain.useCases.GetAllChatsUseCase
import com.example.domain.useCases.GetAllMessageByChatUseCase
import com.example.domain.useCases.SaveNewMessageUseCase
import com.example.domain.useCases.StartNewChatUseCase
import com.example.presentation.utils.ContactHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val getAllChatsUseCase: GetAllChatsUseCase,
    private val saveNewMessageUseCase: SaveNewMessageUseCase,
    private val getAllMessageByChatUseCase: GetAllMessageByChatUseCase
) : ViewModel() {

    private val _selectedContact = MutableStateFlow<Uri?>(null)
    val selectedContact: StateFlow<Uri?> = _selectedContact

    private val _chatList = MutableStateFlow<List<Chat>>(emptyList())//listOf<Song>())
    val chatList: StateFlow<List<Chat>> = _chatList

    private val _messageList = MutableStateFlow<List<Message>>(emptyList())//listOf<Song>())
    val messageList: StateFlow<List<Message>> = _messageList

    init {
        viewModelScope.launch {
            getAllChatsUseCase.invoke("3123445555").collect { chats ->
                _chatList.value = chats
                Log.d("VM", "$chats")
            }

        }
    }

    fun openContacts(pickContactLauncher: ActivityResultLauncher<Intent>) {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        pickContactLauncher.launch(intent)
    }

    fun getContactDetails(contactUri: Uri, context: Context) {

        // Verificar si el permiso está otorgado
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Si el permiso no está otorgado, solicitarlo al usuario
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.READ_CONTACTS),
                123
            )
        } else {
            val phoneNumber = ContactHelper.getContactPhoneNumber(contactUri, context)
            val name = ContactHelper.getContactName(contactUri, context)
            val photo = ContactHelper.getContactPhoto(contactUri, context)
            startNewChat(
                UserContact(
                    name = name ?: "Unknown",
                    phoneNumber = phoneNumber ?: "",
                    profilePicture = photo?.toString() ?: ""
                )
            )
        }


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

    fun addMessage(msg: Message) {
        viewModelScope.launch(Dispatchers.IO) {
            saveNewMessageUseCase.invoke(msg)
        }
    }

    private fun getCurrentUser(): UserContact {
        //TODO("Current User")
        return UserContact(
            name = "Propietario",
            phoneNumber = "3123445555",
            profilePicture = "",
            owner = true
        )
    }

    fun getMessageListByChat(chatId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _messageList.value= emptyList()
            getAllMessageByChatUseCase.invoke(chatId).collect {
                _messageList.value = it
                Log.d("Message2","$it")
            }
        }
    }

}