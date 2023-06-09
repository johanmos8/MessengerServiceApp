package com.example.data.repositories

import android.util.Log
import com.example.data.mappers.ChatFBListToChatListMapper
import com.example.data.mappers.ChatFBToChatMapper
import com.example.data.remotedatasource.ChatRemoteDataSourceImpl
import com.example.domain.models.Chat
import com.example.domain.repositories.IUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val remoteDataSourceImpl: ChatRemoteDataSourceImpl,
    private val chatFBListToChatListMapper: ChatFBListToChatListMapper
) : IUserRepository {
    override suspend fun startNewChat(newChat: Chat) {
        remoteDataSourceImpl.startNewChat(newChat)
    }

    override suspend fun getAllChats(phoneNumber: String): Flow<List<Chat>> {
        return remoteDataSourceImpl.getConversationsForUser(phoneNumber).map {
            chatFBListToChatListMapper(it)
        }

    }
}