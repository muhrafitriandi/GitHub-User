package com.yandey.githubuser.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yandey.githubuser.domain.usecase.*

@Suppress("UNCHECKED_CAST")
class UserViewModelFactory(
    private val app: Application,
    private val searchUsersUseCase: SearchUsersUseCase,
    private val detailUsersUseCase: DetailUsersUseCase,
    private val followersUsersUseCase: FollowersUsersUseCase,
    private val followingUsersUseCase: FollowingUsersUseCase,
    private val insertUserUseCase: InsertUserUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val checkFavoriteUsersUseCase: CheckFavoriteUsersUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(
            app,
            searchUsersUseCase,
            detailUsersUseCase,
            followersUsersUseCase,
            followingUsersUseCase,
            insertUserUseCase,
            getAllUsersUseCase,
            deleteUserUseCase,
            checkFavoriteUsersUseCase
        ) as T
    }
}