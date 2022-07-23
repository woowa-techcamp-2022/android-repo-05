package com.example.android_repo_05.data.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.android_repo_05.data.search.models.RepositoryModel
import com.example.android_repo_05.data.search.paging.RepositoryPagingSource
import kotlinx.coroutines.flow.Flow

class RepositoryRepository {

    companion object {
        private var ReposRepo: RepositoryRepository? = null

        fun getInstance(): RepositoryRepository {
            return ReposRepo ?: synchronized(this) {
                ReposRepo ?: RepositoryRepository().also { ReposRepo = it }
            }
        }
    }

    fun getRepositoryListByPaging(q : String) : Flow<PagingData<RepositoryModel>> {
        return Pager(PagingConfig(pageSize = 10)) {
            RepositoryPagingSource(q)
        }.flow
    }
}