package com.example.android_repo_05.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imgUrl")
fun loadImageFromUrl(view: ImageView, url: String?) {
    if(!url.isNullOrBlank()) {
        Glide.with(view.context)
            .load(url)
            .into(view)
    }
}