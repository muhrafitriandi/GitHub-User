package com.yandey.githubuser.domain.usecase

import com.yandey.githubuser.data.model.UserResponse
import com.yandey.githubuser.data.util.Resource
import com.yandey.githubuser.domain.repository.UserRepository

class FollowersUsersUseCase(private val userRepository: UserRepository) {
    suspend fun execute(username: String): Resource<List<UserResponse>> =
        userRepository.getFollowersUsers(username)
}