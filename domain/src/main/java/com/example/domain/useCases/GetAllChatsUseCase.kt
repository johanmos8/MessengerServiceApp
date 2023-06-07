package com.example.domain.useCases

import com.example.domain.models.Chat
import com.example.domain.repositories.IUserRepository
import javax.inject.Inject

class GetAllChatsUseCase @Inject constructor(
    private val iUserRepository: IUserRepository
) {
    suspend operator fun invoke() = iUserRepository.getAllChats()

}