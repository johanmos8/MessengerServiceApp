package com.example.data.remotedatasource

import android.util.Log
import com.example.data.entities.ChatFB
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
            val listUserKeys = addNewUsers(chat.participants)
            val membersRef = database.child("members")
            listUserKeys.map {
                membersRef.child(conversationId).child(it).setValue(true)
            }
        } else {
            // No se pudo obtener un ID válido para la conversación
            // Manejar esta situación según corresponda
        }


    }

    override suspend fun getConversationsForUser(phoneNumber: String): Flow<List<ChatFB>> =
        callbackFlow {

            val conversationsRef = database.child("members")
            val userConversationsRef = conversationsRef.orderByChild(phoneNumber).equalTo(true)
            val chatList = mutableListOf<ChatFB>()
            //adding listener

            val childEventListener = object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                    val chatId = dataSnapshot.key
                    Log.d("Firebase-", "onChildAdded:" + dataSnapshot.key!!)
                    val chatRef = database.child("chats").child(chatId!!)
                    chatRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val title = snapshot.child("title").getValue(String::class.java)
                            val lastMessage =
                                snapshot.child("lastMessage").getValue(String::class.java)
                            val timestamp = snapshot.child("timestamp").getValue(Long::class.java)

                            if (lastMessage != null && timestamp != null) {
                                val chat = ChatFB(lastMessage, timestamp)
                                chatList.add(chat)
                            }

                            // Verificar si todos los chats han sido cargados
                            if (chatList.size == dataSnapshot.childrenCount.toInt()) {
                                chatList.sortByDescending { it.timestamp }
                                trySend(chatList.toList()) // Enviar los chats al flujo
                                close() // Cerrar el flujo después de enviar los chats
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })

                    Log.d("Prueba", "onChildAdded:" + dataSnapshot.key!!)

                    // A new comment has been added, add it to the displayed list
                    //val comment = dataSnapshot.getValue<Comment>()

                    // ...
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    //TODO("Not yet implemented")
                    Log.d("Firebase", "onChildChanged: ${snapshot.key}")

                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    //TODO("Not yet implemented")
                    Log.d("Firebase", "onChildRemoved: ${snapshot.key}")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    //TODO("Not yet implemented")
                    Log.d("Firebase", "onChildMoved: ${snapshot.key}")

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d("Firebase", "onChildMoved: ${databaseError}")
                }
            }
            userConversationsRef.addChildEventListener(childEventListener)
            awaitClose()

        }.flowOn(Dispatchers.IO)

    fun addMessageToConversation(conversationId: String, message: Message) {
        val conversationRef = database.child("conversations/$conversationId/messages")
        val messageId =
            conversationRef.push().key ?: return // Genera un ID único para el nuevo mensaje

        // Actualiza el mensaje en la base de datos
        conversationRef.child(messageId).setValue(message)
    }


    fun addNewUsers(users: List<UserContact>): List<String> {
        val userRef = database.child("users")
        val keys = mutableListOf<String>()
        users.forEach {
            //val userKey = userRef.push().key
            keys.add(it.phoneNumber)
            database.child("users").child(it.phoneNumber).setValue(it)
                .addOnSuccessListener { exception ->
                    Log.d("Insert", "exitoso usuario $it")
                }.addOnFailureListener { exception ->
                    Log.d("Insert", "fallo usuario $it-${exception.message}")

                }

        }
        return keys
    }

}


/*     override fun onDataChange(dataSnapshot: DataSnapshot) {
         val conversationList = mutableListOf<Chat>()
         for (conversationSnapshot in dataSnapshot.children) {
             val conversation = conversationSnapshot.getValue(Chat::class.java)
             conversation?.let {
                 conversationList.add(it)
             }
         }
         return conversationList
     }*/
