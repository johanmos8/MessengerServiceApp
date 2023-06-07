package com.example.data.remotedatasource

import com.example.domain.models.Chat
import com.example.domain.models.UserContact

interface IChatRemoteDataSource {
    suspend fun startNewChat(chat: Chat)

}