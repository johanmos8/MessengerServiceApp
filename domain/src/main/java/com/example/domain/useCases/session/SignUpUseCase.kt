package com.example.domain.useCases.session

import com.example.domain.models.UserContact
import com.example.domain.repositories.ISessionRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val iSessionRepository: ISessionRepository
) {
    suspend operator fun invoke(user: UserContact) = iSessionRepository.saveUser(user = user)

}