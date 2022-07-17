package com.example.android_repo_05.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.android_repo_05.data.models.IssueResponse
import androidx.paging.map
import com.example.android_repo_05.others.TimeUnit
import com.example.android_repo_05.others.Utils
import com.example.android_repo_05.paging.IssuePagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.ParseException

class IssueRepository {
    companion object {
        private var issueRepo: IssueRepository? = null

        fun getInstance(): IssueRepository {
            return issueRepo ?: synchronized(this) {
                issueRepo ?: IssueRepository().also { issueRepo = it }
            }
        }
    }

    fun getStockDataByPaging() : Flow<PagingData<IssueResponse>> {
        return Pager(PagingConfig(pageSize = 10)) {
            IssuePagingSource()
        }.flow.map { pagingData ->
            pagingData.map { issueResponse ->
                calculElapsedTime(issueResponse)
            }
        }
    }

    private fun calculElapsedTime(issueResponse: IssueResponse) : IssueResponse {
        try {
            val updateTime = Utils.simpleDateFormat.parse(issueResponse.updatedAt)!!.time
            var diffTime = (Utils.curTime - updateTime) / 1000
            if (diffTime < TimeUnit.Second.timeUnit) {
                issueResponse.elapsedTime = "${diffTime}초전"
            } else if (diffTime < TimeUnit.Minute.timeUnit) {
                diffTime /= TimeUnit.Second.timeUnit
                issueResponse.elapsedTime = "${diffTime}분전"
            } else if (diffTime < TimeUnit.Hour.timeUnit) {
                diffTime /= TimeUnit.Minute.timeUnit
                issueResponse.elapsedTime = "${diffTime}시간전"
            } else if (diffTime < TimeUnit.Day.timeUnit) {
                diffTime /= TimeUnit.Hour.timeUnit
                issueResponse.elapsedTime = "${diffTime}일전"
            } else if (diffTime < TimeUnit.MONTH.timeUnit) {
                diffTime /= TimeUnit.Day.timeUnit
                issueResponse.elapsedTime = "${diffTime}개월전"
            } else {
                diffTime /= TimeUnit.MONTH.timeUnit
                issueResponse.elapsedTime = "${diffTime}년전"
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            issueResponse.elapsedTime = ""
        }
        return issueResponse
    }
}