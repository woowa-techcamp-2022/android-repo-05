package com.example.android_repo_05.customview

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.example.android_repo_05.R
import com.google.android.material.button.MaterialButton

class MainTabButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = com.google.android.material.R.attr.materialButtonStyle
) : MaterialButton(context, attrs, defStyle) {
    init {
        setPadding(8,16,8,16)
        backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.navy))
        isClickable = true
        isCheckable = true
        isChecked = false
        addOnCheckedChangeListener { button, isChecked ->
            button.backgroundTintList= when(isChecked) {
                true -> ColorStateList.valueOf(ContextCompat.getColor(context, R.color.primary))
                false -> ColorStateList.valueOf(ContextCompat.getColor(context, R.color.navy))
            }
        }
    }
}