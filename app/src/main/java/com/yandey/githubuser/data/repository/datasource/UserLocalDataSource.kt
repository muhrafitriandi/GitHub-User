package com.yandey.githubuser.data.repository.datasource

import com.yandey.githubuser.data.model.UserResponse
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    suspend fun insertUser(userResponse: UserResponse)
    fun readAllUsers(): Flow<List<UserResponse>>
    suspend fun deleteUser(userResponse: UserResponse)
    fun checkFavoriteUsers(username: String): Flow<Boolean>
}