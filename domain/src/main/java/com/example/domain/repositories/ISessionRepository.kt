package com.example.domain.repositories

import com.example.domain.models.UserContact
import kotlinx.coroutines.flow.Flow

interface ISessionRepository {

    suspend fun loginUser(username: String, password: String): Flow<UserContact>?
    suspend fun logOutUser()
    suspend fun saveUser(user: UserContact):Boolean
}