package com.example.android_repo_05.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.android_repo_05.data.models.RepositoryModel
import com.example.android_repo_05.others.Constants.SEARCH_PAGE_SIZE
import com.example.android_repo_05.others.Constants.STARTING_PAGE_INDEX
import com.example.android_repo_05.retrofit.GithubApiInstance
import retrofit2.HttpException
import java.io.IOException

class RepositoryPagingSource(
    private val query: String
) : PagingSource<Int, RepositoryModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoryModel> {
        return try {
            val nextPageNumber = params.key ?: STARTING_PAGE_INDEX
            val response = GithubApiInstance.retrofit.getRepositories(
                page = nextPageNumber,
                num = SEARCH_PAGE_SIZE,
                query = query
            ).items.map {
                it.mapperToRepositoryModel()
            }

            val prevKey = if (nextPageNumber == STARTING_PAGE_INDEX) {
                null
            } else {
                nextPageNumber - 1
            }

            val nextKey = if (response.isEmpty()) {
                null
            } else {
                nextPageNumber + (params.loadSize / SEARCH_PAGE_SIZE)
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

    override fun getRefreshKey(state: PagingState<Int, RepositoryModel>): Int? {
        return null
    }
}