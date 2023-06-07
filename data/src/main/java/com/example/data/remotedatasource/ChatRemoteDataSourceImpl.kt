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
import io.reactivex.rxjava3.annotations.NonNull
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

    fun getConversationsForUser(userId: String): List<Chat> {

        val conversationsRef = database.child("members")
        val userConversationsRef = conversationsRef.orderByChild(userId).equalTo(true)
        val chatList = mutableListOf<Chat>()
        //adding listener

        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val chatId = dataSnapshot.key
                val chatRef = database.child("chats").child(chatId!!)
                chatRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val chat = snapshot.getValue(Chat::class.java)
                        chat?.let {
                            chatList.add(it)
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
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejo de errores en caso de que la consulta falle
            }
        }
        userConversationsRef.addChildEventListener(childEventListener)
        return chatList

    }

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
            val userKey = userRef.push().key
            keys.add(userKey.toString())
            userKey?.let { it1 ->
                database.child("users").child(it1).setValue(it)
                    .addOnSuccessListener {exception->
                        Log.d("Insert","exitoso usuario $it")
                    }.addOnFailureListener {exception->
                        Log.d("Insert","fallo usuario $it-${exception.message}")

                    }
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
