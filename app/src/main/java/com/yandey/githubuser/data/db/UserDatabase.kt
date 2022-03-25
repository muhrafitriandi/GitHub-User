package com.yandey.githubuser.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yandey.githubuser.data.model.UserResponse

@Database(entities = [UserResponse::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun getUserDAO(): UserDAO
}