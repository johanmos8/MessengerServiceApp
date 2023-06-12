package com.example.data.remotedatasource

import android.util.Log
import com.example.data.entities.ChatFB
import com.example.data.entities.UserFB
import com.example.domain.models.Chat
import com.example.domain.models.Message

import com.example.domain.models.UserContact
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import io.reactivex.rxjava3.annotations.NonNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class ChatRemoteDataSourceImpl @Inject constructor() : IChatRemoteDataSource {
    private val database: DatabaseReference = FirebaseConnection.database

    override suspend fun startNewChat(chat: Chat) {

        val listUserKeys = addNewUsers(chat.participants)
        if (listUserKeys.isNotEmpty()) {

            val conversationRef = database.child("chats")
            // Generar un nuevo ID para la conversación
            val conversationId = conversationRef.push().key
            // Verificar que se haya obtenido el ID correctamente
            if (conversationId != null) {
                val chatData = ChatFB(
                    lastMessage = "Prueba"
                )
                // Actualizar la información del chat en la base de datos
                conversationRef.child(conversationId).setValue(chatData)
                    //conversationRef.child(conversationId).setValue(childUpdates)
                    .addOnSuccessListener {
                        Log.d("Insert", "exitoso")
                    }
                    .addOnFailureListener { exception ->
                        Log.d("Insert", "fallo ${exception.message}")
                    }
                val membersRef = database.child("members")
                listUserKeys.map {
                    membersRef.child(conversationId).child(it).setValue(true)
                }
            } else {
                // No se pudo obtener un ID válido para la conversación
                // Manejar esta situación según corresponda
            }

        }
    }

    override suspend fun getConversationsForUser(phoneNumber: String): Flow<List<ChatFB>> =
        callbackFlow {

            val conversationsRef = database.child("members")

            val userConversationsRef = conversationsRef.orderByChild(phoneNumber).equalTo(true)
            val chatList = mutableListOf<ChatFB>()

            userConversationsRef.addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(
                    membersSnapshot: DataSnapshot,
                    previousChildName: String?
                ) {
                    val chatId = membersSnapshot.key
                    val membersTotal = membersSnapshot.childrenCount
                    val participantIds = membersSnapshot.children.mapNotNull { childSnapshot ->
                        val userId = childSnapshot.key
                        val isParticipant = childSnapshot.getValue(Boolean::class.java)
                        if (userId != null && isParticipant != null && isParticipant) {
                            userId // Crear un objeto UserContact con el ID del usuario
                        } else {
                            null
                        }
                    }

                    val chatsRef = database.child("chats").child(chatId!!)
                    chatsRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(chatSnapshot: DataSnapshot) {
                            val lastMessage =
                                chatSnapshot.child("lastMessage").getValue(String::class.java)
                            val timestamp =
                                chatSnapshot.child("timestamp").getValue(Long::class.java)

                            val participants = mutableListOf<UserFB>()
                            val usersRef = database.child("users")
                            val participantQuery = usersRef.orderByKey().equalTo(participantIds[0])

                            participantQuery.addListenerForSingleValueEvent(object :
                                ValueEventListener {
                                override fun onDataChange(participantsSnapshot: DataSnapshot) {
                                    participantIds.forEach { userId ->
                                        val participantSnapshot = participantsSnapshot.child(userId)
                                        val name = participantSnapshot.child("name")
                                            .getValue(String::class.java)
                                        val phoneNumber = participantSnapshot.child("phoneNumber")
                                            .getValue(String::class.java)
                                        val profilePicture =
                                            participantSnapshot.child("profilePicture")
                                                .getValue(String::class.java)
                                        val status = participantSnapshot.child("status")
                                            .getValue(Boolean::class.java)
                                        val owner = participantSnapshot.child("owner")
                                            .getValue(Boolean::class.java)

                                        if (name != null && phoneNumber != null) {
                                            val participant = UserFB(
                                                name = name,
                                                phoneNumber = phoneNumber,
                                                profilePicture = profilePicture,
                                                status = status ?: true,
                                                owner = owner ?: false
                                            )
                                            participants.add(participant)
                                        }
                                    }

                                    if (lastMessage != null && timestamp != null && participants.isNotEmpty()) {
                                        val chat = ChatFB(
                                            chatId = chatId,
                                            lastMessage = lastMessage,
                                            timestamp = timestamp,
                                            participants = participants
                                        )
                                        chatList.add(chat)
                                        trySend(chatList.toList())


                                    }

                                    /* if (chatList.size == chatSnapshot.childrenCount.toInt()) {
                                          chatList.sortByDescending { it.timestamp }
                                          trySend(chatList.toList())
                                          close()
                                      }*/
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    Log.d("Firebase-Error", "${error.message}")
                                }
                            })
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.d("Firebase-Error", "${error.message}")
                        }
                    })
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    // Implementación vacía de onChildChanged
                    /* val chatId=snapshot.key
                        val participantIds = snapshot.children.mapNotNull { childSnapshot ->
                            val userId = childSnapshot.key
                            val isParticipant = childSnapshot.getValue(Boolean::class.java)
                            if (userId != null && isParticipant != null && isParticipant) {
                                userId // Crear un objeto UserContact con el ID del usuario
                            } else {
                                null
                            }
                        }
                        val chatsRef = database.child("chats").child(chatId!!)
                        chatsRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(chatSnapshot: DataSnapshot) {
                                val lastMessage = chatSnapshot.child("lastMessage").getValue(String::class.java)
                                val timestamp = chatSnapshot.child("timestamp").getValue(Long::class.java)

                                // Buscar el chat correspondiente en la lista existente y actualizar sus propiedades
                                val updatedChat = chatList.find { it.participants.map { participant -> participant.phoneNumber } == participantIds }
                                updatedChat?.let {
                                    it.lastMessage = lastMessage ?: ""
                                    it.timestamp = timestamp ?: 0L
                                }

                                // Notificar que los datos han cambiado
                                trySend(chatList.toList())
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Log.d("Firebase-Error", "${error.message}")
                            }
                        })*/
                }


                override fun onChildRemoved(snapshot: DataSnapshot) {
                    TODO("Not yet implemented")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                // Resto de los métodos de ChildEventListener

                // ...
            })

            awaitClose()
        }.flowOn(Dispatchers.IO)


    fun addMessageToConversation(conversationId: String, message: Message) {
        val conversationRef = database.child("conversations/$conversationId/messages")
        val messageId =
            conversationRef.push().key
                ?: return // Genera un ID único para el nuevo mensaje

        // Actualiza el mensaje en la base de datos
        conversationRef.child(messageId).setValue(message)
    }


    private fun addNewUsers(users: List<UserContact>): List<String> {
        val userRef = database.child("users")
        val keys = mutableListOf<String>()
        val updates = HashMap<String, Any?>()

        users.forEach { user ->
            //val userKey = userRef.push().key
            val phoneNumber = user.phoneNumber
            keys.add(phoneNumber)
            val userValues =
                user.toMap() // Asume que tienes un método "toMap()" en la clase UserContact
            updates["$phoneNumber"] = userValues


        }
        database.child("users").updateChildren(updates)
            .addOnSuccessListener {
                Log.d("Insert", "Exitoso: ${keys.joinToString()}")
            }
            .addOnFailureListener { exception ->
                Log.d("Insert", "Fallo: ${keys.joinToString()}-${exception.message}")
            }
        return keys
    }

}

