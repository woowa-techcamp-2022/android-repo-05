package com.example.android_repo_05.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.android_repo_05.data.model.IssueResponse
import com.example.android_repo_05.paging.IssuePagingSource
import kotlinx.coroutines.flow.Flow

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
        }.flow
    }
}