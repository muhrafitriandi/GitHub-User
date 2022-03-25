package com.yandey.githubuser.data.repository.datasourceimpl

import com.yandey.githubuser.data.api.UserAPIService
import com.yandey.githubuser.data.model.SearchUserResponse
import com.yandey.githubuser.data.model.UserResponse
import com.yandey.githubuser.data.repository.datasource.UserRemoteDataSource
import retrofit2.Response

class UserRemoteDataSourceImpl(private val userAPIService: UserAPIService) : UserRemoteDataSource {
    override suspend fun searchUsers(searchQuery: String): Response<SearchUserResponse> =
        userAPIService.search(searchQuery)

    override suspend fun detailUsers(username: String): Response<UserResponse> =
        userAPIService.detail(username)

    override suspend fun followersUsers(username: String): Response<List<UserResponse>> =
        userAPIService.followers(username)

    override suspend fun followingUsers(username: String): Response<List<UserResponse>> =
        userAPIService.following(username)
}