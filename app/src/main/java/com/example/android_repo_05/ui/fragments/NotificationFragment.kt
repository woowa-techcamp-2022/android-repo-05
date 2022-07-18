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
import com.example.android_repo_05.R
import com.example.android_repo_05.adapters.NotificationAdapter
import com.example.android_repo_05.base.BaseFragment
import com.example.android_repo_05.data.models.ResponseState
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

    override fun initViews() {
        notificationAdapter.addLoadStateListener { state ->
            binding.pbNotification.isVisible = state.source.refresh is LoadState.Loading
        }
        binding.rvNotification.adapter = notificationAdapter
    }
}