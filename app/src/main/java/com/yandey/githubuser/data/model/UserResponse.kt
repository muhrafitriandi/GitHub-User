package com.yandey.githubuser.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yandey.githubuser.data.util.AppConstant

@Entity(tableName = AppConstant.TABLE_NAME)
data class UserResponse(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val avatar_url: String?,
    val bio: String?,
    val blog: String?,
    val company: String?,
    val followers: Int?,
    val following: Int?,
    val html_url: String?,
    val location: String?,
    val login: String?,
    val name: String?,
    val public_repos: Int?
)