package com.example.data.repositories

import com.example.data.localdatasource.DatabaseLocalDataSource
import com.example.data.remotedatasource.ChatRemoteDataSourceImpl
import com.example.domain.models.UserContact
import com.example.domain.repositories.ISessionRepository
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val localDataSource: DatabaseLocalDataSource
) : ISessionRepository {
    override suspend fun loginUser(username:String,password:String) :Boolean{
        return localDataSource.login(username,password)
    }

    override suspend fun logOutUser() {
        localDataSource.logout()
    }
    override suspend fun saveUser(user:UserContact){
        localDataSource.saveUser(user)
    }
}