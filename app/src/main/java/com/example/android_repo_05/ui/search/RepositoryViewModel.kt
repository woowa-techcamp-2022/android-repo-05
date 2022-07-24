package com.example.android_repo_05.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android_repo_05.data.search.models.RepositoryModel
import com.example.android_repo_05.data.search.RepositoryRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val repository: RepositoryRepositoryImpl
) : ViewModel() {

    private val _searchResult = MutableStateFlow<PagingData<RepositoryModel>>(PagingData.empty())
    val searchResult = _searchResult

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

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
//    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
//    val searchResult: Flow<PagingData<RepositoryModel>> = _searchQuery
//        .debounce(700)
//        .flatMapLatest { query ->
//            if (query.isBlank()) emptyFlow()
//            else repositoryFlow(query)
//        }.cachedIn(viewModelScope)

    var searchJob: Job? = null
    private fun cancelSearchJob() = searchJob?.cancel()

    fun searchResult() {
        cancelSearchJob()
        searchJob = viewModelScope.launch {
            delay(700)
            if (_searchQuery.value.isEmpty()) {
                _searchResult.emit(PagingData.empty())
            } else {
                repository.getRepositoryListByPaging(_searchQuery.value).cachedIn(viewModelScope)
                    .collectLatest {
                        _searchResult.emit(it)
                    }
            }
        }
    }

}