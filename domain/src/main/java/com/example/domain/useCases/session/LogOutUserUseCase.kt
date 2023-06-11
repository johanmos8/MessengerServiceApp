package com.example.domain.useCases.session

import com.example.domain.repositories.ISessionRepository
import javax.inject.Inject

class LogOutUserUseCase @Inject constructor(
    private val iSessionRepository: ISessionRepository
) {
    suspend operator fun invoke() = iSessionRepository.logOutUser()

}