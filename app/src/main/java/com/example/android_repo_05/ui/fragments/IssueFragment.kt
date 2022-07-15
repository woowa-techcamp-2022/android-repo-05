package com.example.android_repo_05.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.android_repo_05.R
import com.example.android_repo_05.adapters.IssuePagingAdapter
import com.example.android_repo_05.base.BaseFragment
import com.example.android_repo_05.databinding.FragmentIssueBinding
import com.example.android_repo_05.viewmodels.AppViewModelFactory
import com.example.android_repo_05.viewmodels.IssueViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class IssueFragment : BaseFragment<FragmentIssueBinding>(R.layout.fragment_issue) {
    private val issueViewModel by lazy {
        ViewModelProvider(
            this,
            AppViewModelFactory()
        )[IssueViewModel::class.java]
    }

    private val issueAdapter by lazy { IssuePagingAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                issueViewModel.issueList.collectLatest {
                    issueAdapter.submitData(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                issueAdapter.loadStateFlow.collectLatest {
                    // TODO : 데이터 바인딩으로 처리 가능?
                    binding.loadingProgress.isVisible = it.source.refresh is LoadState.Loading
                    binding.appendProgress.isVisible = it.append is LoadState.Loading
                }
            }
        }
    }

    override fun initViews() {
        binding.rvIssue.adapter = issueAdapter
    }
}