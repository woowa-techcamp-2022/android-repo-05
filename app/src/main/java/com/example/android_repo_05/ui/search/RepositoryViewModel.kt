package com.example.android_repo_05.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android_repo_05.data.search.RepositoryRepository
import com.example.android_repo_05.data.search.models.RepositoryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val repository: RepositoryRepository
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

    fun setSearchFocused(isFocused: Boolean) {
        _isSearchFocused.value = isFocused
    }

    // 아직 debounce와 flatMapLatest는 정식 출시가 안됨..
//    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
//    val searchResult: Flow<PagingData<RepositoryModel>> = _searchQuery
//        .debounce(700)
//        .flatMapLatest { query ->
//            if (query.isBlank()) emptyFlow()
//            else repositoryFlow(query)
//        }.cachedIn(viewModelScope)

    var searchJob: Job? = null
    private fun cancelSearchJob() = searchJob?.cancel()

    fun searchResult(query: String) {
        cancelSearchJob()
        searchJob = viewModelScope.launch {
            if (query.isEmpty()) {
                _searchQuery.value = query
                _searchResult.emit(PagingData.empty())
            } else {
                delay(700)
                _searchQuery.value = query
                repository.getRepositoryListByPaging(query).cachedIn(viewModelScope)
                    .collectLatest {
                        _searchResult.emit(it)
                    }
            }
        }
    }

}