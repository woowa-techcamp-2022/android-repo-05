package com.example.android_repo_05.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.android_repo_05.R
import com.example.android_repo_05.others.Utils.languageColorMap
import java.util.*

@BindingAdapter("imgUrl")
fun loadImageFromUrl(view: ImageView, url: String?) {
    if(!url.isNullOrBlank()) {
        Glide.with(view.context)
            .load(url)
            .into(view)
    }
}

@BindingAdapter("stateImg")
fun setStateImg(view: ImageView, state : String) {
    when(state) {
        "closed" -> view.setImageResource(R.drawable.ic_issueclosed)
        "open" -> view.setImageResource(R.drawable.ic_issueopen)
        else -> view.setImageResource(R.drawable.ic_issueopen)
    }
}

@BindingAdapter("languageClr")
fun setLanguageColor(view: View, language: String) {
    if (language.isEmpty()) view.background = null
    else {
        view.background = ContextCompat.getDrawable(view.context, R.drawable.bg_search_language_color)
        languageColorMap[language]?.let {
            view.backgroundTintList = ColorStateList.valueOf(it)
        } ?: run {
            val rnd = Random()
            languageColorMap[language] = Color.rgb(rnd.nextInt(255),rnd.nextInt(255),rnd.nextInt(255))
            view.backgroundTintList = ColorStateList.valueOf(languageColorMap[language]!!)
        }
    }
}