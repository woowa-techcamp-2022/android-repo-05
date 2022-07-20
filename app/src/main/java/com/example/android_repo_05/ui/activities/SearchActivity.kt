package com.example.android_repo_05.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
                        with(binding) {
                            pbSearchLoading.isVisible = it.refresh is LoadState.Loading
                            pbSearchAppend.isVisible = it.append is LoadState.Loading
                            rvSearchResult.isVisible =
                                it.refresh !is LoadState.Loading && repositoryAdapter.itemCount != 0
                            tvSearchNoResult.isVisible = !tfSearch.text.isNullOrEmpty() &&
                                    it.append.endOfPaginationReached && repositoryAdapter.itemCount == 0
                        }
                    }
                }

                launch {
                    repositoryViewModel.searchQuery.collectLatest { searchQuery ->
                        with(binding) {
                            if (searchQuery.isEmpty()) {
                                repositoryAdapter.submitData(PagingData.empty())
                                lEmptyQuery.isVisible = true
                                rvSearchResult.isVisible = false
                                binding.tvSearchNoResult.isVisible = false
                            } else {
                                lEmptyQuery.isVisible = false
                                rvSearchResult.isVisible = true
                            }
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
        binding.tfSearch.doOnTextChanged { text, start, before, count ->
            repositoryViewModel.setSearchQuery(text.toString())
            binding.tvSearchNoResult.isVisible =
                !text.isNullOrEmpty() && repositoryAdapter.itemCount == 0
        }
    }

}