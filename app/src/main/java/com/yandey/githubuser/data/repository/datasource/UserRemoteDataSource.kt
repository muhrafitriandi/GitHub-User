package com.yandey.githubuser.data.repository.datasource

import com.yandey.githubuser.data.model.SearchUserResponse
import com.yandey.githubuser.data.model.UserResponse
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun searchUsers(searchQuery: String): Response<SearchUserResponse>
    suspend fun detailUsers(username: String): Response<UserResponse>
    suspend fun followersUsers(username: String): Response<List<UserResponse>>
    suspend fun followingUsers(username: String): Response<List<UserResponse>>
}