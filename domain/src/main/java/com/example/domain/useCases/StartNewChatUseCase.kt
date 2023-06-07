package com.example.domain.useCases

import com.example.domain.models.Chat
import com.example.domain.repositories.IUserRepository
import javax.inject.Inject

class StartNewChatUseCase @Inject constructor(
    private val iUserRepository: IUserRepository
) {
    suspend operator fun invoke(newChat: Chat) = iUserRepository.startNewChat(newChat)

}