package com.example.android_repo_05.ui.main.issue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.android_repo_05.data.repositories.IssueRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine

class IssueViewModel(repository: IssueRepository) : ViewModel() {
    private val issueList = repository.getStockDataByPaging().cachedIn(viewModelScope)

    private val _issueFiltering = MutableStateFlow(IssueFiltering.Open)
    val issueFiltering: StateFlow<IssueFiltering> = _issueFiltering

    private val _isDropDownOpened = MutableStateFlow(false)
    val isDropDownOpened: StateFlow<Boolean> = _isDropDownOpened

    val filteredList = issueList.combine(issueFiltering) { issues, issueFilter ->
        issues.filter { issue ->
            issue.state in issueFilter.filterList
        }
    }

    fun setIssueFiltering(filtering: IssueFiltering) {
        _issueFiltering.value = filtering
    }

    fun setIsDropDownOpened(isSpinnerSelected: Boolean) {
        _isDropDownOpened.value = isSpinnerSelected
    }
}