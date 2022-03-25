package com.yandey.githubuser.data.model

data class SearchUserResponse(
    val items: List<UserResponse>,
    val total_count: Int
)