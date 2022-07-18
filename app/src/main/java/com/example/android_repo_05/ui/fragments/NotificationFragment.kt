package com.example.android_repo_05.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.RecyclerView
import com.example.android_repo_05.R
import com.example.android_repo_05.adapters.NotificationAdapter
import com.example.android_repo_05.base.BaseFragment
import com.example.android_repo_05.databinding.FragmentNotificationBinding
import com.example.android_repo_05.ui.viewmodels.AppViewModelFactory
import com.example.android_repo_05.ui.viewmodels.NotificationViewModel
import kotlinx.coroutines.flow.collectLatest
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
    }

    private fun setSwipeCallback() {
        val swipeCallback = object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0, LEFT)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // TODO("Not yet implemented")
            }
        }
        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.rvNotification)
    }
}