package com.example.android_repo_05.ui.search.custom

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

class SearchTextInputEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = com.google.android.material.R.attr.editTextStyle
) : TextInputEditText(context, attrs, defStyle) {

    private var onFocusListener: ((Boolean) -> Unit)? = null

    fun setOnFocusListener(listener: (Boolean) -> Unit) {
        onFocusListener = listener
    }

    override fun onFocusChanged(gainFocus: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect)
        onFocusListener?.invoke(gainFocus)
    }
}