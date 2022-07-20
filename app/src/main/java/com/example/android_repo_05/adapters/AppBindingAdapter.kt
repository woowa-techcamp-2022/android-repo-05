package com.example.android_repo_05.adapters

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.android_repo_05.R
import com.example.android_repo_05.others.GlideApp
import com.example.android_repo_05.others.Utils.dpToPx
import com.example.android_repo_05.others.Utils.languageColorMap
import com.google.android.material.appbar.MaterialToolbar
import java.util.*

@BindingAdapter("imgUrl")
fun loadImageFromUrl(view: ImageView, url: String?) {
    if (!url.isNullOrBlank()) {
        GlideApp.with(view.context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .centerInside()
            .into(view)
    }
}

@BindingAdapter("stateImg")
fun setStateImg(view: ImageView, state: String) {
    when (state) {
        "closed" -> view.setImageResource(R.drawable.ic_issueclosed)
        "open" -> view.setImageResource(R.drawable.ic_issueopen)
        else -> view.setImageResource(R.drawable.ic_issueopen)
    }
}

@BindingAdapter("languageClr")
fun setLanguageColor(view: View, language: String) {
    if (language.isEmpty()) view.background = null
    else {
        view.background =
            ContextCompat.getDrawable(view.context, R.drawable.bg_search_language_color)
        languageColorMap[language]?.let {
            view.backgroundTintList = ColorStateList.valueOf(it)
        } ?: run {
            val rnd = Random()
            languageColorMap[language] =
                Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255))
            view.backgroundTintList = ColorStateList.valueOf(languageColorMap[language]!!)
        }
    }
}

@BindingAdapter("bitmapUrl")
fun loadBitmapFromUrl(toolbar: MaterialToolbar, url: String?) {
    GlideApp.with(toolbar.context).asBitmap().load(url).placeholder(R.drawable.ic_user).circleCrop()
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .override(dpToPx(toolbar.context, 24), dpToPx(toolbar.context, 24))
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                toolbar.menu.findItem(R.id.menu_profile).icon =
                    BitmapDrawable(toolbar.resources, resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                toolbar.menu.findItem(R.id.menu_profile).icon = placeholder
            }
        })
}