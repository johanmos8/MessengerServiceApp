package com.example.data.di

import com.example.data.remotedatasource.ChatRemoteDataSourceImpl
import com.example.data.remotedatasource.IChatRemoteDataSource
import com.example.data.repositories.UserRepositoryImpl
import com.example.domain.repositories.IUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideUserRepository(
        remoteDataSourceImpl: ChatRemoteDataSourceImpl
    ): IUserRepository {
        return UserRepositoryImpl(remoteDataSourceImpl)
    }
}
