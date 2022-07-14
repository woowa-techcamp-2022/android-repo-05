package com.example.android_repo_05.ui.fragments

import android.content.Context
import com.example.android_repo_05.R
import com.example.android_repo_05.base.BaseFragment
import com.example.android_repo_05.databinding.FragmentIssueBinding

class IssueFragment : BaseFragment<FragmentIssueBinding>(R.layout.fragment_issue) {

    private lateinit var mContext : Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun initViews() {
    }

}