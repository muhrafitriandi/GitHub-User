package com.yandey.githubuser.data.repository.datasourceimpl

import com.yandey.githubuser.data.db.UserDAO
import com.yandey.githubuser.data.model.UserResponse
import com.yandey.githubuser.data.repository.datasource.UserLocalDataSource
import kotlinx.coroutines.flow.Flow

class UserLocalDataSourceImpl(private val userDAO: UserDAO) : UserLocalDataSource {
    override suspend fun insertUser(userResponse: UserResponse) = userDAO.insert(userResponse)

    override fun readAllUsers(): Flow<List<UserResponse>> = userDAO.readAll()

    override suspend fun deleteUser(userResponse: UserResponse) = userDAO.delete(userResponse)

    override fun checkFavoriteUsers(username: String): Flow<Boolean> =
        userDAO.favoriteCheck(username)
}