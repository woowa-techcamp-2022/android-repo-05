package com.example.android_repo_05.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_repo_05.databinding.ItemLoadStateBinding
import retrofit2.HttpException
import java.io.IOException

class PagingLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<PagingLoadStateAdapter.PagingLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: PagingLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PagingLoadStateViewHolder {
        return PagingLoadStateViewHolder(
            ItemLoadStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), retry
        )
    }

    class PagingLoadStateViewHolder(
        private val binding: ItemLoadStateBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(state: LoadState) {
            binding.btnLoadState.setOnClickListener { retry() }
            binding.isLoading = state is LoadState.Loading
            binding.isError = state is LoadState.Error
            if (state is LoadState.Error) {
                when (state.error) {
                    is HttpException -> binding.errorMessage = "HTTP Error!!"
                    is IOException -> binding.errorMessage = "I/O Error!!"
                    else -> binding.errorMessage = "Error!!"
                }
            }
        }
    }
}
