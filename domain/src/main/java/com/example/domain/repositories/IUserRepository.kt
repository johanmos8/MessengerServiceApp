package com.example.domain.repositories

import com.example.domain.models.UserContact

interface IUserRepository {
    suspend fun starNewChat(userContact: UserContact)
}