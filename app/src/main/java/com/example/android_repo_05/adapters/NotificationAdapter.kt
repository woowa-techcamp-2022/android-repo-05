package com.example.android_repo_05.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_repo_05.databinding.ItemNotificationBinding

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotificationViewHolder(
        ItemNotificationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        // TODO("Not yet implemented")
    }

    override fun getItemCount() = 15

    inner class NotificationViewHolder(
        private val binding: ItemNotificationBinding
    ) : RecyclerView.ViewHolder(binding.root)
}