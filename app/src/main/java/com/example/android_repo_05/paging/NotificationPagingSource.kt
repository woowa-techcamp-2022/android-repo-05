package com.example.android_repo_05.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.android_repo_05.data.models.notification.NotificationModel
import com.example.android_repo_05.others.Constants.NETWORK_PAGE_SIZE
import com.example.android_repo_05.others.Constants.STARTING_PAGE_INDEX
import com.example.android_repo_05.retrofit.GithubApiInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class NotificationPagingSource : PagingSource<Int, NotificationModel>() {
    override fun getRefreshKey(state: PagingState<Int, NotificationModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NotificationModel> {
        return try {
            val nextPageNumber = params.key ?: STARTING_PAGE_INDEX
            val response = GithubApiInstance.retrofit.getNotification(pageNum = nextPageNumber)
            response.forEach {
                withContext(Dispatchers.IO) {
                    it.commentCount =
                        GithubApiInstance.retrofit.getIssueComments(it.subject.url + "/comments").size
                }
            }
            val prevKey = if (nextPageNumber == STARTING_PAGE_INDEX) {
                null
            } else {
                nextPageNumber - 1
            }
            val nextKey = if (response.isEmpty()) {
                null
            } else {
                nextPageNumber + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(response, prevKey, nextKey)
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch(e: Exception) {
            LoadResult.Error(e)
        }
    }
}