package com.example.android_repo_05.data.profile

import com.example.android_repo_05.data.network.GithubApiService
import com.example.android_repo_05.data.network.ResponseState
import com.example.android_repo_05.data.profile.models.StarredModel
import com.example.android_repo_05.data.profile.models.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val service: GithubApiService
) : UserRepository {

    override suspend fun getUserInfoFromRemote() = withContext(Dispatchers.IO) {
        return@withContext try {
            handleUserResponse(service.getUser())
        } catch (e: Exception) {
            ResponseState.Error(e.message ?: "error")
        }
    }

    override suspend fun getStarredFromRemote(): ResponseState<Int> = withContext(Dispatchers.IO) {
        return@withContext try {
            handleStarredResponse(service.getStarred())
        } catch (e: Exception) {
            ResponseState.Error(e.message ?: "error")
        }
    }

    override fun handleUserResponse(response: Response<UserModel>): ResponseState<UserModel> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return ResponseState.Success(result)
            }
        }
        return ResponseState.Error(response.message())
    }

    override fun handleStarredResponse(response: Response<List<StarredModel>>): ResponseState<Int> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return ResponseState.Success(result.size)
            }
        }
        return ResponseState.Error(response.message())
    }
}