package com.example.android_repo_05.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android_repo_05.data.models.RepositoryModel
import com.example.android_repo_05.data.repositories.RepositoryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class RepositoryViewModel(private val repository: RepositoryRepository) : ViewModel() {

    private fun repositoryFlow(query: String): Flow<PagingData<RepositoryModel>> =
        repository.getRepositoryListByPaging(query)

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: Flow<String> = _searchQuery

    private val _isSearchFocused = MutableStateFlow(true)
    private val isSearchFocused: Flow<Boolean> = _isSearchFocused

    val isStartIconEnabled = searchQuery.combine(isSearchFocused) { searchQuery, isSearchFocused ->
        searchQuery.isEmpty() && !isSearchFocused
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSearchFocused(isFocused: Boolean) {
        _isSearchFocused.value = isFocused
    }

    // TODO : 아직 debounce와 flatMapLatest는 정식 출시가 안됨..
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResult: Flow<PagingData<RepositoryModel>> = _searchQuery
        .debounce(700)
        .flatMapLatest { query ->
            if (query.isBlank()) emptyFlow()
            else repositoryFlow(query)
        }.cachedIn(viewModelScope)
}