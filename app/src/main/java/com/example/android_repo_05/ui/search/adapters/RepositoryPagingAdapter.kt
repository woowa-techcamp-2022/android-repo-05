package com.example.android_repo_05.ui.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android_repo_05.data.models.RepositoryModel
import com.example.android_repo_05.databinding.ItemRepositoryBinding

class RepositoryPagingAdapter :
    PagingDataAdapter<RepositoryModel, RepositoryPagingAdapter.RepositoryViewHolder>(diffCallback = differ) {

    companion object {
        private val differ = object : DiffUtil.ItemCallback<RepositoryModel>() {
            override fun areItemsTheSame(
                oldItem: RepositoryModel,
                newItem: RepositoryModel
            ): Boolean {
                return (oldItem.id == newItem.id)
            }

            override fun areContentsTheSame(
                oldItem: RepositoryModel,
                newItem: RepositoryModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder =
        RepositoryViewHolder(
            ItemRepositoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.binding.repository = getItem(position)
    }

    class RepositoryViewHolder(val binding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root)
}