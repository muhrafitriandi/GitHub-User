package com.yandey.githubuser.domain.usecase

import com.yandey.githubuser.data.model.UserResponse
import com.yandey.githubuser.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetAllUsersUseCase(private val userRepository: UserRepository) {
    fun execute(): Flow<List<UserResponse>> = userRepository.getAllUsersFromDB()
}