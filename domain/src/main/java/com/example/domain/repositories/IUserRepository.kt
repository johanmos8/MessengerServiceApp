package com.example.domain.repositories

import com.example.domain.models.Chat
import com.example.domain.models.UserContact
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    suspend fun startNewChat(newChat: Chat)
    suspend fun getAllChats(): Flow<List<Chat>>
}