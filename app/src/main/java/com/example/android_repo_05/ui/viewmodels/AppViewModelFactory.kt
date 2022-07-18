package com.example.android_repo_05.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_05.data.repositories.*
import com.example.android_repo_05.paging.NotificationPagingSource

class AppViewModelFactory(
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TokenViewModel::class.java) -> {
                TokenViewModel(TokenRepository.getInstance()) as T
            }
            modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                UserViewModel(UserRepository.getInstance()) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(ProfileImageRepository.getInstance()) as T
            }
            modelClass.isAssignableFrom(IssueViewModel::class.java) -> {
                IssueViewModel(IssueRepository.getInstance()) as T
            }
            modelClass.isAssignableFrom(NotificationViewModel::class.java) -> {
                NotificationViewModel(NotificationRepository.getInstance(NotificationPagingSource())) as T
            }
            modelClass.isAssignableFrom(RepositoryViewModel::class.java) -> {
                RepositoryViewModel(RepositoryRepository.getInstance()) as T
            }
            else -> {
                super.create(modelClass)
            }
        }
    }
}