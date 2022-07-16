package com.example.android_repo_05.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.android_repo_05.repositories.IssueRepository
import com.example.android_repo_05.ui.fragments.IssueFiltering
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class IssueViewModel(repository : IssueRepository) : ViewModel() {
    val issueList = repository.getStockDataByPaging().cachedIn(viewModelScope)

    private val _issueFiltering = MutableStateFlow<IssueFiltering>(IssueFiltering.Open)
    val issueFiltering : StateFlow<IssueFiltering> = _issueFiltering

    fun setIssueFiltering(filtering : IssueFiltering) {
        _issueFiltering.value = filtering
    }

}