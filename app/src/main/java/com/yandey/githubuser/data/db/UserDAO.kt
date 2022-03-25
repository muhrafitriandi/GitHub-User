package com.yandey.githubuser.data.db

import androidx.room.*
import com.yandey.githubuser.data.model.UserResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userResponse: UserResponse)

    @Query("SELECT * FROM users ORDER by id ASC")
    fun readAll(): Flow<List<UserResponse>>

    @Delete
    suspend fun delete(userResponse: UserResponse)

    @Query("SELECT EXISTS(SELECT * FROM users WHERE login = :username)")
    fun favoriteCheck(username: String): Flow<Boolean>
}