package com.example.data.repositories

import com.example.data.remotedatasource.MessageRemoteDataSourceImpl
import com.example.domain.models.UserContact
import com.example.domain.repositories.IUserRepository

class UserRepositoryImpl(
    private val remoteDataSourceImpl: MessageRemoteDataSourceImpl
) : IUserRepository {
    override suspend fun starNewChat(userContact: UserContact) {
        TODO("Not yet implemented")
    }
}