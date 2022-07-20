package com.example.android_repo_05.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.android_repo_05.data.models.IssueResponse
import com.example.android_repo_05.others.Constants.ISSUE_PAGE_SIZE
import com.example.android_repo_05.others.Constants.STARTING_PAGE_INDEX
import com.example.android_repo_05.retrofit.GithubApiInstance.retrofit

class IssuePagingSource : PagingSource<Int, IssueResponse>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, IssueResponse> {
        // TODO : 네트워크 및 데이터 에러 처리 필요.
        val pageNumber = params.key ?: STARTING_PAGE_INDEX
        val issueList = retrofit.getIssues(
            page = pageNumber,
            num = ISSUE_PAGE_SIZE
        )
        val nextKey = if (issueList.isEmpty()) {
            null
        } else {
            pageNumber + (params.loadSize / ISSUE_PAGE_SIZE)
        }
        return LoadResult.Page(
            data = issueList,
            prevKey = when (pageNumber) {
                STARTING_PAGE_INDEX -> null
                else -> pageNumber - 1
            },
            nextKey = nextKey
        )
    }

    override fun getRefreshKey(state: PagingState<Int, IssueResponse>): Int? {
        return null
    }
}