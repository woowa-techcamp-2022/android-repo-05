package com.example.android_repo_05.customviews

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.android_repo_05.R
import com.example.android_repo_05.databinding.IssueFilteringSpinnerBinding

class IssueFilteringSpinner @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private val binding by lazy {IssueFilteringSpinnerBinding.inflate(LayoutInflater.from(context), this, false) }

    init {
        addView(binding.root)
        getAttrs(attrs)
    }

    private fun getAttrs(attrs : AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.IssueSpinner)
        setTypedArray(typedArray)
    }

    private fun setTypedArray(typedArray: TypedArray) {
        val spinnerTitle = typedArray.getString(R.styleable.IssueSpinner_spinner_title) ?: context.getString(R.string.issue_spinner_open)
        setSpinnerTitle(spinnerTitle)
    }

    private fun setSpinnerTitle(title : String) {
        binding.tvIssueSpinner.text = title
    }

}