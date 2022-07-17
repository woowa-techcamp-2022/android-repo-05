package com.example.android_repo_05.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.android_repo_05.R

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