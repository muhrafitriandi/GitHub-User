package com.yandey.githubuser.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.*
import com.yandey.githubuser.data.model.SearchUserResponse
import com.yandey.githubuser.data.model.UserResponse
import com.yandey.githubuser.data.util.Resource
import com.yandey.githubuser.domain.repository.DataStoreRepository
import com.yandey.githubuser.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserViewModel(
    private val app: Application,
    private val searchUsersUseCase: SearchUsersUseCase,
    private val detailUsersUseCase: DetailUsersUseCase,
    private val followersUsersUseCase: FollowersUsersUseCase,
    private val followingUsersUseCase: FollowingUsersUseCase,
    private val insertUserUseCase: InsertUserUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val checkFavoriteUsersUseCase: CheckFavoriteUsersUseCase
) : AndroidViewModel(app) {
    val searchUsers: MutableLiveData<Resource<SearchUserResponse>> = MutableLiveData()
    val detailUsers: MutableLiveData<Resource<UserResponse>> = MutableLiveData()
    val followUsers: MutableLiveData<Resource<List<UserResponse>>> = MutableLiveData()

    private val repository = DataStoreRepository(app)

    fun saveThemeSetting(isDarkModeActive: Boolean) = viewModelScope.launch {
        repository.saveThemeSetting(isDarkModeActive)
    }

    fun getThemeSetting() = repository.getThemeSetting().asLiveData(Dispatchers.IO)

    fun searchUsers(searchQuery: String) = viewModelScope.launch {
        searchUsers.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val response = searchUsersUseCase.execute(searchQuery)
                searchUsers.postValue(response)
            } else {
                searchUsers.postValue(Resource.Error("No internet connection."))
            }
        } catch (e: Exception) {
            searchUsers.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun detailUsers(username: String) = viewModelScope.launch {
        detailUsers.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val response = detailUsersUseCase.execute(username)
                detailUsers.postValue(response)
            } else {
                detailUsers.postValue(Resource.Error("No internet connection."))
            }
        } catch (e: Exception) {
            detailUsers.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun followersUsers(username: String) = viewModelScope.launch {
        followUsers.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val response = followersUsersUseCase.execute(username)
                followUsers.postValue(response)
            } else {
                followUsers.postValue(Resource.Error("No internet connection."))
            }
        } catch (e: Exception) {
            followUsers.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun followingUsers(username: String) = viewModelScope.launch {
        followUsers.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val response = followingUsersUseCase.execute(username)
                followUsers.postValue(response)
            } else {
                followUsers.postValue(Resource.Error("No internet connection."))
            }
        } catch (e: Exception) {
            followUsers.postValue(Resource.Error(e.message.toString()))
        }
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    fun insertUser(userResponse: UserResponse) = viewModelScope.launch {
        insertUserUseCase.execute(userResponse)
    }

    fun getAllUsers() = liveData {
        getAllUsersUseCase.execute().collect {
            emit(it)
        }
    }

    fun deleteUser(userResponse: UserResponse) = viewModelScope.launch {
        deleteUserUseCase.execute(userResponse)
    }

    fun checkFavoriteUsers(username: String) = liveData {
        checkFavoriteUsersUseCase.execute(username).collect {
            emit(it)
        }
    }
}