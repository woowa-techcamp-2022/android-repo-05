package com.example.android_repo_05.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.filter
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.android_repo_05.R
import com.example.android_repo_05.adapters.NotificationAdapter
import com.example.android_repo_05.adapters.helpers.NotificationItemHelperCallback
import com.example.android_repo_05.base.BaseFragment
import com.example.android_repo_05.data.models.ResponseState
import com.example.android_repo_05.databinding.FragmentNotificationBinding
import com.example.android_repo_05.ui.viewmodels.AppViewModelFactory
import com.example.android_repo_05.ui.viewmodels.NotificationViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class NotificationFragment :
    BaseFragment<FragmentNotificationBinding>(R.layout.fragment_notification) {

    private val notificationAdapter by lazy { NotificationAdapter() }
    private val notificationViewModel by lazy {
        ViewModelProvider(
            this,
            AppViewModelFactory()
        )[NotificationViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObserver()
        setSwipeCallback()
    }

    override fun initViews() {
        notificationAdapter.addLoadStateListener { state ->
            binding.pbNotification.isVisible = state.source.refresh is LoadState.Loading
        }
        binding.rvNotification.adapter = notificationAdapter
    }

    private fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                notificationViewModel.notificationList.collectLatest {
                    notificationAdapter.submitData(it)
                }
            }
        }
        notificationViewModel.changeResponse.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResponseState.Loading -> binding.pbNotification.isVisible = true
                is ResponseState.Success -> binding.pbNotification.isVisible = false
                is ResponseState.Error -> restoreItem()
            }
        }
    }

    private fun setSwipeCallback() {
        val swipeCallback = NotificationItemHelperCallback(requireContext()) { position ->
            removeItemCallback(position)
        }
        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.rvNotification)
    }

    private fun restoreItem() {
        binding.pbNotification.isVisible = false
        notificationViewModel.restoreItem()
        viewLifecycleOwner.lifecycleScope.launch {
            notificationViewModel.notificationList.collectLatest {
                notificationAdapter.submitData(it)
            }
        }
        Toast.makeText(requireContext(), "읽음 처리를 실패했습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun removeItemCallback(position: Int) {
        notificationViewModel.changeNotificationAsRead(notificationAdapter.snapshot().items[position].url)
        notificationViewModel.removeItem(notificationAdapter.snapshot().items[position].id)
        viewLifecycleOwner.lifecycleScope.launch {
            notificationViewModel.notificationList.collectLatest {
                notificationAdapter.submitData(it)
            }
        }
    }
}