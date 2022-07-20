package com.example.android_repo_05.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.android_repo_05.databinding.ItemIssueFilterBinding
import com.example.android_repo_05.databinding.ItemIssueFilterHeadBinding
import com.example.android_repo_05.ui.fragments.IssueFiltering

class IssueSpinnerAdapter(
    private val context: Context
) : BaseAdapter() {

    private var selectedFilter: IssueFiltering = IssueFiltering.Open
    private var isSpinnerSelected: Boolean = false

    fun setSelectedFilter(selectedFilter: IssueFiltering) {
        this.selectedFilter = selectedFilter
    }

    fun setSpinnerSelected(isSpinnerSelected: Boolean) {
        this.isSpinnerSelected = isSpinnerSelected
        notifyDataSetChanged()
    }

    override fun getCount(): Int = IssueFiltering.values().size

    override fun getItem(position: Int): IssueFiltering = IssueFiltering.values()[position]

    override fun getItemId(position: Int): Long = 0

    /*
        데이터 바인딩 적용시 깜빡거림 현상이 있어 뷰바인딩 적용!
     */
    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val binding =
            ItemIssueFilterHeadBinding.inflate(LayoutInflater.from(context), viewGroup, false)
        binding.tvFilterHead.text = IssueFiltering.values()[position].filterName
        binding.ivFilterHead.scaleY = if (this.isSpinnerSelected) -1F else 1F
        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val binding = ItemIssueFilterBinding.inflate(LayoutInflater.from(context), viewGroup, false)
        binding.issueFilter = IssueFiltering.values()[position]
        binding.selectedFilter = this.selectedFilter
        return binding.root
    }
}