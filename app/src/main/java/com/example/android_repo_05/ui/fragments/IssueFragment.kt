package com.example.android_repo_05.ui.fragments

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
import androidx.paging.filter
import com.example.android_repo_05.R
import com.example.android_repo_05.adapters.IssuePagingAdapter
import com.example.android_repo_05.adapters.IssueSpinnerAdapter
import com.example.android_repo_05.base.BaseFragment
import com.example.android_repo_05.databinding.FragmentIssueBinding
import com.example.android_repo_05.viewmodels.AppViewModelFactory
import com.example.android_repo_05.viewmodels.IssueViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class IssueFragment : BaseFragment<FragmentIssueBinding>(R.layout.fragment_issue) {
    private val issueViewModel by lazy {
        ViewModelProvider(
            this,
            AppViewModelFactory()
        )[IssueViewModel::class.java]
    }

    private lateinit var activityContext : Context
    private val issueAdapter by lazy { IssuePagingAdapter() }
    private val issueSpinnerAdapter by lazy {IssueSpinnerAdapter(activityContext)}

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
                issueViewModel.issueList.combine(issueViewModel.issueFiltering) { issueList, issueFiltering ->
                    issueList.filter { issue ->
                        issue.state in issueFiltering.filterList
                    }
                }.collectLatest {
                    issueAdapter.submitData(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                issueAdapter.loadStateFlow.collectLatest {
                    // TODO : 데이터 바인딩으로 처리 가능?
                    binding.pgLoading.isVisible = it.source.refresh is LoadState.Loading
                    binding.pgAppend.isVisible = it.append is LoadState.Loading
                }
            }
        }
    }

    override fun initViews() {
        binding.rvIssue.adapter = issueAdapter
        binding.spIssueFiltering.adapter = issueSpinnerAdapter
        binding.spIssueFiltering.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapter: AdapterView<*>?, view : View?, position: Int, id: Long) {
                issueViewModel.setIssueFiltering(IssueFiltering.values()[position])
                issueSpinnerAdapter.setSelectedFilter(IssueFiltering.values()[position])
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
}

enum class IssueFiltering(val filterName : String, val filterList : List<String>) {
    Open("Open", listOf("open")),
    Close("Close",listOf("closed")),
    All("All",listOf("open","closed"))
}