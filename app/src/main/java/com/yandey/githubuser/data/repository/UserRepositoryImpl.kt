package com.yandey.githubuser.data.repository

import com.yandey.githubuser.data.model.SearchUserResponse
import com.yandey.githubuser.data.model.UserResponse
import com.yandey.githubuser.data.repository.datasource.UserLocalDataSource
import com.yandey.githubuser.data.repository.datasource.UserRemoteDataSource
import com.yandey.githubuser.data.util.Resource
import com.yandey.githubuser.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {
    override suspend fun getSearchUsers(searchQuery: String): Resource<SearchUserResponse> =
        responseToResourceSearchUsers(userRemoteDataSource.searchUsers(searchQuery))

    override suspend fun getDetailUsers(username: String): Resource<UserResponse> =
        responseToResourceDetailUsers(userRemoteDataSource.detailUsers(username))

    override suspend fun getFollowersUsers(username: String): Resource<List<UserResponse>> =
        responseToResourceFollow(userRemoteDataSource.followersUsers(username))

    override suspend fun getFollowingUsers(username: String): Resource<List<UserResponse>> =
        responseToResourceFollow(userRemoteDataSource.followingUsers(username))

    override suspend fun insertUserToDB(userResponse: UserResponse) =
        userLocalDataSource.insertUser(userResponse)

    override fun getAllUsersFromDB(): Flow<List<UserResponse>> = userLocalDataSource.readAllUsers()

    override suspend fun deleteUserFromDB(userResponse: UserResponse) =
        userLocalDataSource.deleteUser(userResponse)

    override fun checkFavoriteUsersFromDB(username: String): Flow<Boolean> =
        userLocalDataSource.checkFavoriteUsers(username)

    private fun responseToResourceSearchUsers(
        response: Response<SearchUserResponse>
    ): Resource<SearchUserResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToResourceDetailUsers(
        response: Response<UserResponse>
    ): Resource<UserResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToResourceFollow(
        response: Response<List<UserResponse>>
    ): Resource<List<UserResponse>> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}