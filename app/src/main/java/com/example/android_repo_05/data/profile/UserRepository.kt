package com.example.android_repo_05.data.profile

import com.example.android_repo_05.data.network.ResponseState
import com.example.android_repo_05.data.profile.models.StarredModel
import com.example.android_repo_05.data.profile.models.UserModel
import com.example.android_repo_05.data.network.GithubApiInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserRepository {
    companion object {
        private var userRepo: UserRepository? = null

        fun getInstance(): UserRepository {
            return userRepo ?: synchronized(this) {
                userRepo ?: UserRepository().also { userRepo = it }
            }
        }
    }

    suspend fun getUserInfoFromRemote() = withContext(Dispatchers.IO) {
        return@withContext try {
            handleUserResponse(GithubApiInstance.retrofit.getUser())
        } catch (e: Exception) {
            ResponseState.Error(e.message ?: "error")
        }
    }

    suspend fun getStarredFromRemote() = withContext(Dispatchers.IO) {
        return@withContext try {
            handleStarredResponse(GithubApiInstance.retrofit.getStarred())
        } catch (e: Exception) {
            ResponseState.Error(e.message ?: "error")
        }
    }

    private fun handleUserResponse(response: Response<UserModel>): ResponseState<UserModel> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return ResponseState.Success(result)
            }
        }
        return ResponseState.Error(response.message())
    }

    private fun handleStarredResponse(response: Response<List<StarredModel>>): ResponseState<Int> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return ResponseState.Success(result.size)
            }
        }
        return ResponseState.Error(response.message())
    }
}