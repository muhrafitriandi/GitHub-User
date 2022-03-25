package com.yandey.githubuser.data.api

import com.yandey.githubuser.data.model.SearchUserResponse
import com.yandey.githubuser.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserAPIService {
    @GET("search/users")
    suspend fun search(
        @Query("q")
        searchQuery: String
    ): Response<SearchUserResponse>

    @GET("users/{username}")
    suspend fun detail(
        @Path("username")
        username: String
    ): Response<UserResponse>

    @GET("users/{username}/followers")
    suspend fun followers(
        @Path("username")
        username: String
    ): Response<List<UserResponse>>

    @GET("users/{username}/following")
    suspend fun following(
        @Path("username")
        username: String
    ): Response<List<UserResponse>>
}