package com.example.android_repo_05.data.main.issue

import androidx.paging.PagingData
import com.example.android_repo_05.data.main.issue.models.IssueResponse
import kotlinx.coroutines.flow.Flow

interface IssueRepository {

    fun getIssueByPaging() : Flow<PagingData<IssueResponse>>

}