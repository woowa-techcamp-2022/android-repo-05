package com.example.android_repo_05.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.android_repo_05.repositories.IssueRepository

class IssueViewModel(repository : IssueRepository) : ViewModel() {
    val issueList = repository.getStockDataByPaging().cachedIn(viewModelScope)

    private var _issueFiltering : MutableLiveData<String> = MutableLiveData()
    val issueFiltering : LiveData<String> = _issueFiltering

}