package com.example.domain.useCases

import com.example.domain.models.Message
import com.example.domain.repositories.IUserRepository
import javax.inject.Inject

class SaveNewMessageUseCase @Inject constructor(
    private val iUserRepository: IUserRepository
) {
    suspend operator fun invoke(message: Message) = iUserRepository.saveNewMessage(message)

}