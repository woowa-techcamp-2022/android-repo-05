package com.example.android_repo_05.ui.main.notification.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android_repo_05.data.models.notification.NotificationModel
import com.example.android_repo_05.databinding.ItemNotificationBinding

class NotificationAdapter
    : PagingDataAdapter<NotificationModel, NotificationAdapter.NotificationViewHolder>(differ) {

    companion object {
        private val differ = object : DiffUtil.ItemCallback<NotificationModel>() {
            override fun areItemsTheSame(
                oldItem: NotificationModel,
                newItem: NotificationModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: NotificationModel,
                newItem: NotificationModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotificationViewHolder(
        ItemNotificationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class NotificationViewHolder(
        private val binding: ItemNotificationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NotificationModel?) {
            binding.notification = data
        }
    }
}