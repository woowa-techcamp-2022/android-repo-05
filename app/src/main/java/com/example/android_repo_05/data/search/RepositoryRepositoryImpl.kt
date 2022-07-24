package com.example.android_repo_05.data.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.android_repo_05.data.network.GithubApiService
import com.example.android_repo_05.data.search.models.RepositoryModel
import com.example.android_repo_05.data.search.paging.RepositoryPagingSource
import com.example.android_repo_05.utils.Constants.SEARCH_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryRepositoryImpl @Inject constructor(
    private val service: GithubApiService
) : RepositoryRepository {

    override fun getRepositoryListByPaging(q: String): Flow<PagingData<RepositoryModel>> {
        return Pager(PagingConfig(pageSize = SEARCH_PAGE_SIZE)) {
            RepositoryPagingSource(q, service)
        }.flow
    }
}