package com.example.domain.useCases

import com.example.domain.models.UserContact
import com.example.domain.repositories.IUserRepository
import javax.inject.Inject

class StarNewChatUseCase @Inject constructor(
    private val iUserRepository: IUserRepository
) {
    suspend operator fun invoke(userContact: UserContact) = iUserRepository.starNewChat(userContact)

}