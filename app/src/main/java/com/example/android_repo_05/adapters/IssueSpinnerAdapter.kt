package com.example.android_repo_05.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.example.android_repo_05.R
import com.example.android_repo_05.databinding.ItemIssueFilterBinding
import com.example.android_repo_05.databinding.ItemIssueFilterHeadBinding
import com.example.android_repo_05.ui.fragments.IssueFiltering

class IssueSpinnerAdapter(
    private val context: Context
) : BaseAdapter() {

    private var selectedFilter: IssueFiltering = IssueFiltering.Open

    fun setSelectedFilter(selectedFilter: IssueFiltering) {
        this.selectedFilter = selectedFilter
    }

    override fun getCount(): Int = IssueFiltering.values().size

    override fun getItem(position: Int): IssueFiltering = IssueFiltering.values()[position]

    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val binding = ItemIssueFilterHeadBinding.inflate(LayoutInflater.from(context), viewGroup, false)
        binding.tvFilterHead.text = IssueFiltering.values()[position].filterName
        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val binding = ItemIssueFilterBinding.inflate(LayoutInflater.from(context), viewGroup, false)
        with(binding) {
            tvFilterList.text = IssueFiltering.values()[position].filterName
            if (selectedFilter == IssueFiltering.values()[position]) {
                tvFilterList.setTextColor(ContextCompat.getColor(context, R.color.white))
                ivFilterCheck.visibility = View.VISIBLE
            } else {
                tvFilterList.setTextColor(ContextCompat.getColor(context, R.color.grey))
                ivFilterCheck.visibility = View.INVISIBLE
            }
        }
        return binding.root
    }

}