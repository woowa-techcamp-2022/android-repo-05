package com.example.android_repo_05.ui.main.issue.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSpinner

class IssueFilteringSpinner @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : AppCompatSpinner(context, attrs, defStyle) {

    private var onDropDownEventsListener: OnDropDownEventsListener? = null
    private var isDropDownOpened: Boolean = false

    fun setOnDropDownListener(listener: OnDropDownEventsListener) {
        onDropDownEventsListener = listener
    }

    interface OnDropDownEventsListener {
        fun dropDownOpen()
        fun dropDownClose()
    }

    override fun performClick(): Boolean {
        isDropDownOpened = true
        onDropDownEventsListener?.dropDownOpen()
        return super.performClick()
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        if (isDropDownOpened && hasWindowFocus) {
            performClosedEvent()
        }
    }

    private fun performClosedEvent() {
        isDropDownOpened = false
        onDropDownEventsListener?.dropDownClose()
    }
}