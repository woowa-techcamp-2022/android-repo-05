package com.example.android_repo_05.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_05.repositories.GithubApiRepository

class LoginViewModelFactory(
    private val githubApiRepository: GithubApiRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(githubApiRepository) as T
    }
}