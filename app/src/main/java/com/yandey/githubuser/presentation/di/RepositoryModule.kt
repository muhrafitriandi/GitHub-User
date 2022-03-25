package com.yandey.githubuser.presentation.di

import com.yandey.githubuser.data.repository.UserRepositoryImpl
import com.yandey.githubuser.data.repository.datasource.UserLocalDataSource
import com.yandey.githubuser.data.repository.datasource.UserRemoteDataSource
import com.yandey.githubuser.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideUserRepository(
        userRemoteDataSource: UserRemoteDataSource,
        userLocalDataSource: UserLocalDataSource
    ): UserRepository =
        UserRepositoryImpl(userRemoteDataSource, userLocalDataSource)
}