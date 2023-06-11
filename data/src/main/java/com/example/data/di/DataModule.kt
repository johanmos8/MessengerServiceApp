package com.example.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.data.localdatasource.DatabaseLocalDataSource
import com.example.data.localdatasource.dataStore
import com.example.data.mappers.ChatFBListToChatListMapper
import com.example.data.remotedatasource.ChatRemoteDataSourceImpl
import com.example.data.remotedatasource.IChatRemoteDataSource
import com.example.data.repositories.SessionRepositoryImpl
import com.example.data.repositories.UserRepositoryImpl
import com.example.domain.repositories.ISessionRepository
import com.example.domain.repositories.IUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Singleton
    @Provides
    fun provideRemoteDataSourceImpl(

    ): IChatRemoteDataSource {
        return ChatRemoteDataSourceImpl()
    }

    @Provides
    @Singleton
    fun provideChatFBListToChatListMapper(): ChatFBListToChatListMapper {
        return ChatFBListToChatListMapper()
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        remoteDataSourceImpl: ChatRemoteDataSourceImpl,
        chatFBListToChatListMapper: ChatFBListToChatListMapper
    ): IUserRepository {
        return UserRepositoryImpl(
            remoteDataSourceImpl,
            chatFBListToChatListMapper
        )
    }
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
    @Provides
    @Singleton
    fun provideSessionRepository(
        localDataSource: DatabaseLocalDataSource
    ): ISessionRepository {
        return SessionRepositoryImpl(
            localDataSource
        )
    }
}
