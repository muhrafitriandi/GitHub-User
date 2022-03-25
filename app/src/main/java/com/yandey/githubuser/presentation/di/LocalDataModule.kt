package com.yandey.githubuser.presentation.di

import com.yandey.githubuser.data.db.UserDAO
import com.yandey.githubuser.data.repository.datasource.UserLocalDataSource
import com.yandey.githubuser.data.repository.datasourceimpl.UserLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {
    @Singleton
    @Provides
    fun provideUserLocalDataSource(userDAO: UserDAO): UserLocalDataSource =
        UserLocalDataSourceImpl(userDAO)
}