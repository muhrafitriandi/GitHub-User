package com.yandey.githubuser.presentation.di

import com.yandey.githubuser.domain.repository.UserRepository
import com.yandey.githubuser.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideSearchUsersUseCase(userRepository: UserRepository): SearchUsersUseCase =
        SearchUsersUseCase(userRepository)

    @Singleton
    @Provides
    fun provideDetailUsersUseCase(userRepository: UserRepository): DetailUsersUseCase =
        DetailUsersUseCase(userRepository)

    @Singleton
    @Provides
    fun provideFollowersUsersUseCase(userRepository: UserRepository): FollowersUsersUseCase =
        FollowersUsersUseCase(userRepository)

    @Singleton
    @Provides
    fun provideFollowingUsersUseCase(userRepository: UserRepository): FollowingUsersUseCase =
        FollowingUsersUseCase(userRepository)

    @Singleton
    @Provides
    fun provideInsertUserUseCase(userRepository: UserRepository): InsertUserUseCase =
        InsertUserUseCase(userRepository)

    @Singleton
    @Provides
    fun provideGetAllUsersUseCase(userRepository: UserRepository): GetAllUsersUseCase =
        GetAllUsersUseCase(userRepository)

    @Singleton
    @Provides
    fun provideDeleteUserUseCase(userRepository: UserRepository): DeleteUserUseCase =
        DeleteUserUseCase(userRepository)

    @Singleton
    @Provides
    fun provideCheckFavoriteUsersUseCase(userRepository: UserRepository): CheckFavoriteUsersUseCase =
        CheckFavoriteUsersUseCase(userRepository)
}