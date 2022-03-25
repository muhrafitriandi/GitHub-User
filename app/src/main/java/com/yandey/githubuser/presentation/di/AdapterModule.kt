package com.yandey.githubuser.presentation.di

import com.yandey.githubuser.presentation.adapter.UserAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {
    @Singleton
    @Provides
    fun provideUserAdapter(): UserAdapter = UserAdapter()
}