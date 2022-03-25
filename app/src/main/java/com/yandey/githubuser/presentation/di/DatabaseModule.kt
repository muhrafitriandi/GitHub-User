package com.yandey.githubuser.presentation.di

import android.app.Application
import androidx.room.Room
import com.yandey.githubuser.data.db.UserDAO
import com.yandey.githubuser.data.db.UserDatabase
import com.yandey.githubuser.data.util.AppConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideUserDatabase(app: Application): UserDatabase =
        Room.databaseBuilder(app, UserDatabase::class.java, AppConstant.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideUserDAO(userDatabase: UserDatabase): UserDAO = userDatabase.getUserDAO()
}