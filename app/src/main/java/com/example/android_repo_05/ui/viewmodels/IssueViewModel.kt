package com.example.android_repo_05.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging. filter
import com.example.android_repo_05.data.repositories.IssueRepository
import com.example.android_repo_05.ui.fragments.IssueFiltering
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine

class IssueViewModel(repository : IssueRepository) : ViewModel() {
    val issueList = repository.getStockDataByPaging().cachedIn(viewModelScope)

    private val _issueFiltering = MutableStateFlow<IssueFiltering>(IssueFiltering.Open)
    val issueFiltering : StateFlow<IssueFiltering> = _issueFiltering

    val filteredList = issueList.combine(issueFiltering) { issueList, issueFiltering ->
        issueList.filter { issue ->
            issue.state in issueFiltering.filterList
        }
    }

    fun setIssueFiltering(filtering : IssueFiltering) {
        _issueFiltering.value = filtering
    }

}