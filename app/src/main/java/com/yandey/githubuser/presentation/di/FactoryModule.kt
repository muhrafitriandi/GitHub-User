package com.yandey.githubuser.presentation.di

import android.app.Application
import com.yandey.githubuser.domain.usecase.*
import com.yandey.githubuser.presentation.viewmodel.UserViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {
    @Singleton
    @Provides
    fun provideUserViewModelFactory(
        app: Application,
        searchUsersUseCase: SearchUsersUseCase,
        detailUsersUseCase: DetailUsersUseCase,
        followersUsersUseCase: FollowersUsersUseCase,
        followingUsersUseCase: FollowingUsersUseCase,
        insertUserUseCase: InsertUserUseCase,
        getAllUsersUseCase: GetAllUsersUseCase,
        deleteUserUseCase: DeleteUserUseCase,
        checkFavoriteUsersUseCase: CheckFavoriteUsersUseCase
    ): UserViewModelFactory = UserViewModelFactory(
        app,
        searchUsersUseCase,
        detailUsersUseCase,
        followersUsersUseCase,
        followingUsersUseCase,
        insertUserUseCase,
        getAllUsersUseCase,
        deleteUserUseCase,
        checkFavoriteUsersUseCase
    )
}