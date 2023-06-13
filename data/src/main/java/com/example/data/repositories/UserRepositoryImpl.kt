package com.example.data.repositories

import com.example.data.mappers.ChatFBListToChatListMapper
import com.example.data.mappers.MessageFBListToMessageListMapper
import com.example.data.remotedatasource.ChatRemoteDataSourceImpl
import com.example.domain.models.Chat
import com.example.domain.models.Message
import com.example.domain.repositories.IUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val remoteDataSourceImpl: ChatRemoteDataSourceImpl,
    private val chatFBListToChatListMapper: ChatFBListToChatListMapper,
    private val messageFBListToMessageListMapper: MessageFBListToMessageListMapper
) : IUserRepository {
    override suspend fun startNewChat(newChat: Chat) {
        remoteDataSourceImpl.startNewChat(newChat)
    }

    override suspend fun getAllChats(phoneNumber: String): Flow<List<Chat>> {
        return remoteDataSourceImpl.getConversationsForUser(phoneNumber).map {
            chatFBListToChatListMapper(it)
        }

    }

    override suspend fun saveNewMessage(msg: Message) {
        return remoteDataSourceImpl.addMessageToConversation(msg)
    }

    override suspend fun getAllMessageByChat(chatId: String): Flow<List<Message>> {
        return remoteDataSourceImpl.getMessageByChat(chatId).map {
            messageFBListToMessageListMapper(it)
        }

    }
}