package com.example.android_repo_05.data.main.notification.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.android_repo_05.data.main.notification.models.NotificationModel
import com.example.android_repo_05.data.network.GithubApiInstance
import com.example.android_repo_05.data.network.GithubApiService
import com.example.android_repo_05.utils.Constants
import com.example.android_repo_05.utils.Constants.STARTING_PAGE_INDEX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class NotificationPagingSource(
    private val service: GithubApiService
) : PagingSource<Int, NotificationModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NotificationModel> {
        return try {
            val nextPageNumber = params.key ?: STARTING_PAGE_INDEX
            val response = withContext(Dispatchers.IO) {
                // IO 작업을 위한 IODispatcher 블럭 생성
                service.getNotification(pageNum = nextPageNumber).onEach {
                    launch {
                        // comment list 불러오는 작업을 각기 다른 코루틴에서 수행하도록 launch
                        if (it.subject.url != null) {
                            service.getComments(it.subject.url).apply {
                                it.commentCount = this.comments
                                it.number =
                                    if (this.number == null || this.number == 0) "" else "#${this.number}"
                            }
                        }
                    }
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
                nextPageNumber + (params.loadSize / Constants.NOTIFICATION_PAGE_SIZE)
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

    override fun getRefreshKey(state: PagingState<Int, NotificationModel>): Int? {
        return null
    }
}