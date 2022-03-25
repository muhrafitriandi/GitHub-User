package com.yandey.githubuser.presentation.di

import com.yandey.githubuser.data.api.UserAPIService
import com.yandey.githubuser.data.repository.datasource.UserRemoteDataSource
import com.yandey.githubuser.data.repository.datasourceimpl.UserRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {
    @Singleton
    @Provides
    fun provideUserRemoteDataSource(userAPIService: UserAPIService): UserRemoteDataSource =
        UserRemoteDataSourceImpl(userAPIService)
}