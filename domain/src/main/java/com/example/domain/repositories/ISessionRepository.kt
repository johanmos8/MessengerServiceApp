package com.example.domain.repositories

import com.example.domain.models.UserContact

interface ISessionRepository {

    suspend fun loginUser(username: String, password: String): Boolean
    suspend fun logOutUser()
    suspend fun saveUser(user: UserContact)
}