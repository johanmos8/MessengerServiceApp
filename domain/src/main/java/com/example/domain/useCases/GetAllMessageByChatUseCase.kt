package com.example.domain.useCases

import com.example.domain.repositories.IUserRepository
import javax.inject.Inject

class GetAllMessageByChatUseCase @Inject constructor(
    private val iUserRepository: IUserRepository
) {
    suspend operator fun invoke(chatId: String) = iUserRepository.getAllMessageByChat(chatId)

}