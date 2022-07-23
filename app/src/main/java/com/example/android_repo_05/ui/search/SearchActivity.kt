package com.example.android_repo_05.ui.search

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.android_repo_05.ui.common.adapters.PagingLoadStateAdapter
import com.example.android_repo_05.ui.search.adapters.RepositoryPagingAdapter
import com.example.android_repo_05.databinding.ActivitySearchBinding
import com.example.android_repo_05.ui.common.AppViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
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
        observe()
        initViews()
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
                    repositoryAdapter.loadStateFlow.combine(repositoryViewModel.searchQuery) { loadState, searchQuery ->
                        if (searchQuery.isEmpty()) {
                            SearchType.NoQuery
                        } else if (loadState.refresh is LoadState.Loading) {
                            SearchType.Loading
                        } else if (loadState.append.endOfPaginationReached && repositoryAdapter.itemCount == 0) {
                            SearchType.NoResult
                        } else {
                            SearchType.Result
                        }
                    }.collectLatest {
                        binding.searchState = it
                    }
                }

                launch {
                    repositoryViewModel.isStartIconEnabled.collectLatest {
                        binding.isStartIconEnabled = it
                    }
                }
            }
        }
    }

    private fun initViews() {
        binding.lifecycleOwner = this

        if (binding.tfSearch.text.isNullOrEmpty()) {
            binding.searchState = SearchType.NoQuery
        }

        binding.tbSearch.setNavigationOnClickListener {
            finish()
        }

        binding.rvSearchResult.adapter = repositoryAdapter.withLoadStateFooter(
            PagingLoadStateAdapter { repositoryAdapter.refresh() }
        )

        binding.tfSearch.doOnTextChanged { text, _, _, _ ->
            repositoryViewModel.setSearchQuery(text.toString())
            repositoryViewModel.searchResult()
        }

        binding.tfSearch.setOnFocusListener { isFocused ->
            repositoryViewModel.setSearchFocused(isFocused)
        }

        binding.srlSearch.setOnRefreshListener {
            binding.srlSearch.isRefreshing = false
            repositoryAdapter.refresh()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus is EditText && ev?.action == MotionEvent.ACTION_DOWN) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            currentFocus!!.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }
}

enum class SearchType {
    NoQuery, Loading, Result, NoResult
}