package com.example.android_repo_05.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.android_repo_05.adapters.RepositoryPagingAdapter
import com.example.android_repo_05.databinding.ActivitySearchBinding
import com.example.android_repo_05.ui.viewmodels.AppViewModelFactory
import com.example.android_repo_05.ui.viewmodels.RepositoryViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

class SearchActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    private val repositoryAdapter by lazy { RepositoryPagingAdapter() }

    private val repositoryViewModel by lazy {
        ViewModelProvider(this, AppViewModelFactory())[RepositoryViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        observe()
    }

    private fun observe() {
        this.lifecycleScope.launch {
            this@SearchActivity.repeatOnLifecycle(Lifecycle.State.STARTED) {
                repositoryViewModel.repositoryFlow("kotlin").collectLatest {
                    repositoryAdapter.submitData(it)
                }
            }
        }

        this.lifecycleScope.launch {
            this@SearchActivity.repeatOnLifecycle(Lifecycle.State.STARTED) {
                repositoryAdapter.loadStateFlow.collectLatest {
                    binding.pgSearchLoading.isVisible = it.source.refresh is LoadState.Loading
                    binding.pgSearchAppend.isVisible = it.append is LoadState.Loading
                }
            }
        }
    }

    private fun initViews() {
        binding.tbSearch.setNavigationOnClickListener {
            finish()
        }
        binding.rvSearchResult.adapter = repositoryAdapter
    }

}