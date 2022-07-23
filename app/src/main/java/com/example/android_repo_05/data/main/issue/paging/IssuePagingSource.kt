package com.example.android_repo_05.data.main.issue.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.android_repo_05.data.main.issue.models.IssueResponse
import com.example.android_repo_05.others.Constants.ISSUE_PAGE_SIZE
import com.example.android_repo_05.others.Constants.STARTING_PAGE_INDEX
import com.example.android_repo_05.data.network.GithubApiInstance.retrofit
import retrofit2.HttpException
import java.io.IOException

class IssuePagingSource : PagingSource<Int, IssueResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, IssueResponse> {
        return try {
            val nextPageNumber = params.key ?: STARTING_PAGE_INDEX
            val response = retrofit.getIssues(page = nextPageNumber, num = ISSUE_PAGE_SIZE)

            val prevKey = if (nextPageNumber == STARTING_PAGE_INDEX) {
                null
            } else {
                nextPageNumber - 1
            }

            val nextKey = if (response.isEmpty()) {
                null
            } else {
                nextPageNumber + (params.loadSize / ISSUE_PAGE_SIZE)
            }

            LoadResult.Page(response, prevKey, nextKey)
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, IssueResponse>): Int? {
        return null
    }
}