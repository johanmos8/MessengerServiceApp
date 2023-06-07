package com.example.data.repositories

import com.example.data.remotedatasource.ChatRemoteDataSourceImpl
import com.example.domain.models.Chat
import com.example.domain.repositories.IUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val remoteDataSourceImpl: ChatRemoteDataSourceImpl
) : IUserRepository {
    override suspend fun startNewChat(newChat: Chat) {
        remoteDataSourceImpl.startNewChat(newChat)
    }

    override suspend fun getAllChats(): Flow<List<Chat>> {
        return flow<List<Chat>> {
            emit(remoteDataSourceImpl.getConversationsForUser("unique"))
        }.flowOn(Dispatchers.IO)
    }
}