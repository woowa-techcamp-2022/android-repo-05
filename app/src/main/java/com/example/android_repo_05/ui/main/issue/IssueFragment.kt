package com.example.android_repo_05.ui.main.issue

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.android_repo_05.R
import com.example.android_repo_05.ui.main.issue.adapters.IssuePagingAdapter
import com.example.android_repo_05.ui.main.issue.adapters.IssueSpinnerAdapter
import com.example.android_repo_05.ui.common.adapters.PagingLoadStateAdapter
import com.example.android_repo_05.ui.common.BaseFragment
import com.example.android_repo_05.ui.main.issue.custom.IssueFilteringSpinner
import com.example.android_repo_05.databinding.FragmentIssueBinding
import com.example.android_repo_05.ui.common.AppViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class IssueFragment : BaseFragment<FragmentIssueBinding>(R.layout.fragment_issue) {
    private val issueViewModel by lazy {
        ViewModelProvider(
            this,
            AppViewModelFactory()
        )[IssueViewModel::class.java]
    }

    private lateinit var activityContext: Context
    private val issueAdapter by lazy { IssuePagingAdapter() }
    private val issueSpinnerAdapter by lazy { IssueSpinnerAdapter(activityContext) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.activityContext = context
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    issueViewModel.filteredList.collectLatest {
                        issueAdapter.submitData(it)
                    }
                }

                launch {
                    issueAdapter.loadStateFlow.collectLatest {
                        // TODO : 데이터 바인딩으로 처리 가능?
                        with(binding) {
                            pbLoading.isVisible = it.source.refresh is LoadState.Loading
                            srlIssue.isVisible =
                                it.refresh !is LoadState.Loading && issueAdapter.itemCount != 0
                            tvIssueNoResult.isVisible =
                                it.append.endOfPaginationReached && issueAdapter.itemCount == 0
                        }
                    }
                }

                launch {
                    issueViewModel.issueFiltering.collectLatest {
                        issueSpinnerAdapter.setSelectedFilter(it)
                    }
                }

                launch {
                    issueViewModel.isDropDownOpened.collectLatest {
                        issueSpinnerAdapter.setSpinnerSelected(it)
                        binding.isDropDownOpened = it
                    }
                }
            }
        }
    }

    override fun initViews() {
        binding.rvIssue.adapter = issueAdapter.withLoadStateFooter(
            PagingLoadStateAdapter { issueAdapter.refresh() }
        )
        binding.srlIssue.setOnRefreshListener {
            binding.srlIssue.isRefreshing = false
            issueAdapter.refresh()
        }
        binding.spIssueFiltering.adapter = issueSpinnerAdapter
        initDropDownEvent()
    }

    private fun initDropDownEvent() {
        with(binding.spIssueFiltering) {
            setOnDropDownListener(object : IssueFilteringSpinner.OnDropDownEventsListener {
                override fun dropDownOpen() {
                    issueViewModel.setIsDropDownOpened(true)
                }

                override fun dropDownClose() {
                    issueViewModel.setIsDropDownOpened(false)
                }
            })

            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapter: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    issueViewModel.setIssueFiltering(IssueFiltering.values()[position])
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // TODO("Not yet implemented")
                }
            }
        }
    }
}

enum class IssueFiltering(val filterName: String, val filterList: List<String>) {
    Open("Open", listOf("open")),
    Close("Close", listOf("closed")),
    All("All", listOf("open", "closed"))
}