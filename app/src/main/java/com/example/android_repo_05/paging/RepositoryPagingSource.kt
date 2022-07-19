package com.example.android_repo_05.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.android_repo_05.data.models.RepositoryModel
import com.example.android_repo_05.others.Constants.SEARCH_PAGE_SIZE
import com.example.android_repo_05.others.Constants.STARTING_PAGE_INDEX
import com.example.android_repo_05.retrofit.GithubApiInstance

class RepositoryPagingSource(
    private val query: String
) : PagingSource<Int, RepositoryModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoryModel> {
        val pageNumber = params.key ?: STARTING_PAGE_INDEX
        val searchList = GithubApiInstance.retrofit.getRepositories(
            page = pageNumber,
            num = SEARCH_PAGE_SIZE,
            query = query
        ).items.map {
            it.mapperToRepositoryModel()
        }
        val nextKey = if (searchList.isEmpty()) {
            null
        } else {
            pageNumber + (params.loadSize / SEARCH_PAGE_SIZE)
        }
        return LoadResult.Page(
            data = searchList,
            prevKey = when (pageNumber) {
                STARTING_PAGE_INDEX -> null
                else -> pageNumber - 1
            },
            nextKey = nextKey
        )
    }

    override fun getRefreshKey(state: PagingState<Int, RepositoryModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}