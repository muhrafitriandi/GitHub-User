package com.yandey.githubuser.domain.usecase

import com.yandey.githubuser.data.model.SearchUserResponse
import com.yandey.githubuser.data.util.Resource
import com.yandey.githubuser.domain.repository.UserRepository

class SearchUsersUseCase(private val userRepository: UserRepository) {
    suspend fun execute(searchQuery: String): Resource<SearchUserResponse> =
        userRepository.getSearchUsers(searchQuery)
}