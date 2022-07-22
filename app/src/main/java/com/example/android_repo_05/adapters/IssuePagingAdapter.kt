package com.example.android_repo_05.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android_repo_05.data.models.IssueResponse
import com.example.android_repo_05.databinding.ItemIssueBinding

class IssuePagingAdapter :
    PagingDataAdapter<IssueResponse, IssuePagingAdapter.IssueViewHolder>(diffCallback = differ){

    companion object {
        private val differ = object : DiffUtil.ItemCallback<IssueResponse>() {
            override fun areItemsTheSame(oldItem: IssueResponse, newItem: IssueResponse): Boolean {
                return (oldItem.id == newItem.id)
            }

            override fun areContentsTheSame(oldItem: IssueResponse, newItem: IssueResponse): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder =
        IssueViewHolder(
            ItemIssueBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.binding.issue = getItem(position)
    }

    class IssueViewHolder(val binding : ItemIssueBinding) : RecyclerView.ViewHolder(binding.root)
}