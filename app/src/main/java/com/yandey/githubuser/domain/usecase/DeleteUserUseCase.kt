package com.yandey.githubuser.domain.usecase

import com.yandey.githubuser.data.model.UserResponse
import com.yandey.githubuser.domain.repository.UserRepository

class DeleteUserUseCase(private val userRepository: UserRepository) {
    suspend fun execute(userResponse: UserResponse) = userRepository.deleteUserFromDB(userResponse)
}