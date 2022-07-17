package com.example.android_repo_05.ui.fragments

import android.os.Bundle
import android.view.View
import com.example.android_repo_05.R
import com.example.android_repo_05.adapters.NotificationAdapter
import com.example.android_repo_05.base.BaseFragment
import com.example.android_repo_05.databinding.FragmentNotificationBinding

class NotificationFragment :
    BaseFragment<FragmentNotificationBinding>(R.layout.fragment_notification) {

    private val notificationAdapter by lazy { NotificationAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun initViews() {
        binding.rvNotification.adapter = notificationAdapter
    }
}