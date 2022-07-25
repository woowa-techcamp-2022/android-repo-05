package com.example.android_repo_05.data.main.issue

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.android_repo_05.data.main.issue.models.IssueResponse
import com.example.android_repo_05.data.main.issue.paging.IssuePagingSource
import com.example.android_repo_05.data.network.GithubApiService
import com.example.android_repo_05.utils.Constants.ISSUE_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IssueRepositoryImpl @Inject constructor(
    private val service: GithubApiService
) : IssueRepository {

    override fun getIssueByPaging(): Flow<PagingData<IssueResponse>> {
        return Pager(PagingConfig(pageSize = ISSUE_PAGE_SIZE)) {
            IssuePagingSource(service)
        }.flow
    }

}