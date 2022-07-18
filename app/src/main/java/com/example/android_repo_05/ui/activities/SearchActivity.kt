package com.example.android_repo_05.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
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
                launch {
                    repositoryViewModel.searchResult.collectLatest {
                        repositoryAdapter.submitData(it)
                    }
                }

                launch {
                    repositoryAdapter.loadStateFlow.collectLatest {
                        binding.pgSearchLoading.isVisible = it.source.refresh is LoadState.Loading
                        binding.pgSearchAppend.isVisible = it.append is LoadState.Loading
                    }
                }

                launch {
                    repositoryViewModel.searchQuery.collectLatest { searchQuery ->
                        if (searchQuery.isEmpty()) {
                            repositoryAdapter.submitData(PagingData.empty())
                            binding.lEmptyQuery.visibility = View.VISIBLE
                            binding.rvSearchResult.visibility = View.INVISIBLE
                        } else {
                            binding.lEmptyQuery.visibility = View.INVISIBLE
                            binding.rvSearchResult.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun initViews() {
        binding.tbSearch.setNavigationOnClickListener {
            finish()
        }
        binding.rvSearchResult.adapter = repositoryAdapter
        // TODO : 화면 재생성시 다시 데이터 가져옴..
        binding.tfSearch.setText(repositoryViewModel.searchQuery.value)
        binding.tfSearch.doOnTextChanged { text, start, before, count ->
            repositoryViewModel.setSearchQuery(text.toString())
        }
    }

}