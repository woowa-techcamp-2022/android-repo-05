package com.example.android_repo_05.data.search

import androidx.paging.PagingData
import com.example.android_repo_05.data.search.models.RepositoryModel
import kotlinx.coroutines.flow.Flow

interface RepositoryRepository {

    fun getRepositoryListByPaging(q : String) : Flow<PagingData<RepositoryModel>>

}