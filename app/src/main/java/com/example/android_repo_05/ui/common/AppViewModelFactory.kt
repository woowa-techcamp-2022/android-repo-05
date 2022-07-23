package com.example.android_repo_05.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_05.data.main.issue.IssueRepository
import com.example.android_repo_05.data.main.ProfileImageRepository
import com.example.android_repo_05.data.login.TokenRepository
import com.example.android_repo_05.data.main.notification.NotificationRepository
import com.example.android_repo_05.data.profile.UserRepository
import com.example.android_repo_05.data.search.RepositoryRepository
import com.example.android_repo_05.ui.login.TokenViewModel
import com.example.android_repo_05.ui.main.issue.IssueViewModel
import com.example.android_repo_05.ui.main.MainViewModel
import com.example.android_repo_05.ui.main.notification.NotificationViewModel
import com.example.android_repo_05.ui.profile.UserViewModel
import com.example.android_repo_05.ui.search.RepositoryViewModel

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
                NotificationViewModel(NotificationRepository.getInstance()) as T
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