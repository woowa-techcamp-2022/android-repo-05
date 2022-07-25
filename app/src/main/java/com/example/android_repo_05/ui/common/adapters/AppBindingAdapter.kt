package com.example.android_repo_05.ui.common.adapters

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.android_repo_05.R
import com.example.android_repo_05.data.network.ResponseState
import com.example.android_repo_05.ui.main.MainViewModel
import com.example.android_repo_05.ui.main.custom.MainTabButton
import com.example.android_repo_05.utils.ColorUtils.languageColorMap
import com.example.android_repo_05.utils.DimenUtils.dpToPx
import com.example.android_repo_05.utils.modules.GlideApp
import com.google.android.material.appbar.MaterialToolbar
import java.util.*

@BindingAdapter("checkTabButton")
fun checkTabButton(tabGroup : ConstraintLayout, id : Int) {
    tabGroup.children.forEach { view ->
        if (view is MainTabButton) {
            view.isChecked = view.id == id
        }
    }
}

@BindingAdapter("clickTabButton")
fun clickTabButton(tabButton: MainTabButton, mainViewModel : MainViewModel) {
    tabButton.setOnClickListener { button ->
        mainViewModel.setCurrentTab(button.id)
        (button as MainTabButton).isChecked = true
    }
}

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

@BindingAdapter("showOnSuccess")
fun showOnSuccess(view : View, responseState: ResponseState<*>?) {
    view.visibility = if (responseState is ResponseState.Success) View.VISIBLE else View.GONE
}

@BindingAdapter("showOnLoading")
fun showOnLoading(view : View, responseState: ResponseState<*>?) {
    view.visibility = if (responseState is ResponseState.Loading) View.VISIBLE else View.GONE
}

@BindingAdapter("showOnError")
fun showError(view : View, responseState: ResponseState<*>?) {
    view.visibility = if (responseState is ResponseState.Error) View.VISIBLE else View.GONE
}