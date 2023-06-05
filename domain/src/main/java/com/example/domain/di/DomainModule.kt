package com.example.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    /*@Provides
    @Singleton
    fun provideSongUseCase(songRepository: ISongRepository): MusicUseCase {
        return MusicUseCase(songRepository)
    }*/
}
