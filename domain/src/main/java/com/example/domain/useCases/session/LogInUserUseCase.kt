package com.example.domain.useCases.session

import com.example.domain.repositories.ISessionRepository
import javax.inject.Inject

class LogInUserUseCase @Inject constructor(
    private val iSessionRepository: ISessionRepository
) {
    suspend operator fun invoke(username: String, password: String) =
        iSessionRepository.loginUser(username, password)

}