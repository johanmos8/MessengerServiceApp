package com.example.data.repositories

import android.util.Log
import com.example.data.localdatasource.DatabaseLocalDataSource
import com.example.domain.models.UserContact
import com.example.domain.repositories.ISessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val localDataSource: DatabaseLocalDataSource
) : ISessionRepository {
    override suspend fun loginUser(username:String,password:String) : Flow<UserContact>? {
        val response= localDataSource.login(username,password)
        Log.d("DataStore", "response: $response")
        return response
    }

    override suspend fun logOutUser() {
        localDataSource.logout()
    }
    override suspend fun saveUser(user:UserContact): Boolean{
        val response= localDataSource.saveUser(user)
        Log.d("DataStore", "response: $response")
        return response
    }
}