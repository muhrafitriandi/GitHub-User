package com.yandey.githubuser.domain.usecase

import com.yandey.githubuser.domain.repository.UserRepository

class CheckFavoriteUsersUseCase(private val userRepository: UserRepository) {
    fun execute(username: String) = userRepository.checkFavoriteUsersFromDB(username)
}