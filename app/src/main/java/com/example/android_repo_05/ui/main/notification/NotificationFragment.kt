package com.example.android_repo_05.ui.main.notification

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.android_repo_05.R
import com.example.android_repo_05.data.network.ResponseState
import com.example.android_repo_05.databinding.FragmentNotificationBinding
import com.example.android_repo_05.ui.common.BaseFragment
import com.example.android_repo_05.ui.common.adapters.PagingLoadStateAdapter
import com.example.android_repo_05.ui.main.notification.adapters.NotificationAdapter
import com.example.android_repo_05.ui.main.notification.helpers.NotificationItemHelperCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotificationFragment :
    BaseFragment<FragmentNotificationBinding>(R.layout.fragment_notification) {

    private val notificationAdapter by lazy { NotificationAdapter() }
    private val notificationViewModel: NotificationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObserver()
        setSwipeCallback()
    }

    override fun initViews() {
        binding.rvNotification.adapter = notificationAdapter.withLoadStateFooter(
            PagingLoadStateAdapter { notificationAdapter.retry() }
        )
        binding.srlNotification.setOnRefreshListener {
            binding.srlNotification.isRefreshing = false
            notificationAdapter.refresh()
        }
    }

    private fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    notificationViewModel.notificationList.collectLatest {
                        notificationAdapter.submitData(it)
                    }
                }

                launch {
                    notificationAdapter.loadStateFlow.collectLatest {
                        with(binding) {
                            pbNotification.isVisible = it.refresh is LoadState.Loading
                            srlNotification.isVisible =
                                it.refresh !is LoadState.Loading && notificationAdapter.itemCount != 0
                            tvNotificationNoResult.isVisible =
                                it.append.endOfPaginationReached && notificationAdapter.itemCount == 0
                        }
                    }
                }
            }
        }
        notificationViewModel.changeResponse.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResponseState.Loading -> binding.pbNotificationAppend.isVisible = true
                is ResponseState.Success -> binding.pbNotificationAppend.isVisible = false
                is ResponseState.Error -> restoreItem()
            }
        }
    }

    private fun setSwipeCallback() {
        ItemTouchHelper(
            NotificationItemHelperCallback(requireContext()) { position ->
                removeItemCallback(position)
            }
        ).attachToRecyclerView(binding.rvNotification)
    }

    private fun restoreItem() {
        binding.pbNotificationAppend.isVisible = false
        notificationViewModel.restoreItem()
        applyChangeToAdapter()
        Toast.makeText(requireContext(), "읽음 처리를 실패했습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun removeItemCallback(position: Int) {
        notificationViewModel.changeNotificationAsRead(notificationAdapter.snapshot().items[position].url)
        notificationViewModel.removeItem(notificationAdapter.snapshot().items[position].id)
        applyChangeToAdapter()
    }

    private fun applyChangeToAdapter() {
        viewLifecycleOwner.lifecycleScope.launch {
            notificationViewModel.notificationList.collectLatest {
                notificationAdapter.submitData(it)
            }
        }
    }
}