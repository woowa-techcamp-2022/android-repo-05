package com.example.android_repo_05.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.android_repo_05.data.models.IssueResponse
import com.example.android_repo_05.retrofit.GithubApiInstance.retrofit

private const val STARTING_PAGE_INDEX = 1
private const val NETWORK_PAGE_SIZE = 10

class IssuePagingSource : PagingSource<Int, IssueResponse>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, IssueResponse> {

        // TODO : 네트워크 및 데이터 에러 처리 필요.
        val pageNumber = params.key ?: STARTING_PAGE_INDEX
        val issueList = retrofit.getIssues(
            page = pageNumber
        )
        val nextKey = if (issueList.isEmpty()) {
            null
        } else {
            pageNumber + (params.loadSize / NETWORK_PAGE_SIZE)
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
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}