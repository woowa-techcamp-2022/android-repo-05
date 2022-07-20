package com.example.android_repo_05.ui.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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
                        binding.pgSearchLoading.isVisible = it.refresh is LoadState.Loading
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

                launch {
                    repositoryViewModel.isStartIconEnabled.collectLatest {
                        binding.isStartIconEnabled = it
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
        }

        binding.tfSearch.setOnFocusListener { isFocused ->
            repositoryViewModel.setSearchFocused(isFocused)
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