package com.yandey.githubuser.domain.repository

import com.yandey.githubuser.data.model.SearchUserResponse
import com.yandey.githubuser.data.model.UserResponse
import com.yandey.githubuser.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getSearchUsers(searchQuery: String): Resource<SearchUserResponse>
    suspend fun getDetailUsers(username: String): Resource<UserResponse>
    suspend fun getFollowersUsers(username: String): Resource<List<UserResponse>>
    suspend fun getFollowingUsers(username: String): Resource<List<UserResponse>>
    suspend fun insertUserToDB(userResponse: UserResponse)
    fun getAllUsersFromDB(): Flow<List<UserResponse>>
    suspend fun deleteUserFromDB(userResponse: UserResponse)
    fun checkFavoriteUsersFromDB(username: String): Flow<Boolean>
}