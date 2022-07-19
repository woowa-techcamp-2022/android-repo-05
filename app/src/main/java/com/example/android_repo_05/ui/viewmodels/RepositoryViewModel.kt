package com.example.android_repo_05.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android_repo_05.data.models.RepositoryModel
import com.example.android_repo_05.data.repositories.RepositoryRepository
import kotlinx.coroutines.flow.Flow

class RepositoryViewModel(private val repository: RepositoryRepository) : ViewModel() {

    fun repositoryFlow(query: String): Flow<PagingData<RepositoryModel>> =
        repository.getRepositoryListByPaging(query).cachedIn(viewModelScope)

}