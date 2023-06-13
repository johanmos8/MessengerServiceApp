package com.example.data.remotedatasource

import com.example.data.entities.ChatFB
import com.example.data.entities.MessageFB
import com.example.domain.models.Chat
import com.example.domain.models.Message
import kotlinx.coroutines.flow.Flow

interface IChatRemoteDataSource {
    suspend fun startNewChat(chat: Chat)

    suspend fun getConversationsForUser(phoneNumber: String): Flow<List<ChatFB>>
    suspend fun addMessageToConversation(msg: Message)
    suspend fun getMessageByChat(chatId: String): Flow<List<MessageFB>>
}