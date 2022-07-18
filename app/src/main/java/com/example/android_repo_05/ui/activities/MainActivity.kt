package com.example.android_repo_05.ui.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.android_repo_05.R
import com.example.android_repo_05.customviews.MainTabButton
import com.example.android_repo_05.data.models.ResponseState
import com.example.android_repo_05.databinding.ActivityMainBinding
import com.example.android_repo_05.data.repositories.ProfileImageRepository
import com.example.android_repo_05.ui.fragments.IssueFragment
import com.example.android_repo_05.ui.fragments.NotificationFragment
import com.example.android_repo_05.ui.viewmodels.AppViewModelFactory
import com.example.android_repo_05.ui.viewmodels.MainViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainViewModel by lazy {
        ViewModelProvider(this, AppViewModelFactory())[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mainViewModel.getProfileUrlFromRemote()
        initViews()
        observe()
    }

    private fun initViews() {
        // 메인 탭 버튼 그룹의 클릭리스너 등록
        binding.tabBtnGroup.children.forEach { view ->
            if (view is MainTabButton) {
                view.setOnClickListener(this)
            }
        }

        binding.tbMain.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_profile -> startActivity(Intent(this, ProfileActivity::class.java))
                R.id.menu_search -> startActivity(Intent(this, SearchActivity::class.java))
            }
            true
        }
    }

    private fun observe() {
        mainViewModel.currentTabFragment.observe(this) {
            checkButton(it.id)
            changeFragment(it)
        }

        mainViewModel.profileUrl.observe(this) { responseState ->
            if(responseState is ResponseState.Success) {
                drawProfileImage(responseState.data!!.profileImageUrl)
            }
        }

    }

    private fun checkButton(id: Int) {
        binding.tabBtnGroup.children.forEach { view ->
            if (view is MainTabButton) {
                view.isChecked = view.id == id
            }
        }
    }

    override fun onClick(view: View) {
        checkButton(view.id)
        mainViewModel.setCurrentTab(view.id)
    }

    private fun getFragment(mainTabType: MainTabType): Fragment =
        when (mainTabType) {
            MainTabType.ISSUE -> IssueFragment()
            MainTabType.NOTIFICATION -> NotificationFragment()
        }

    private fun changeFragment(mainTabType: MainTabType) {
        val transaction = supportFragmentManager.beginTransaction()
        var targetFragment = supportFragmentManager.findFragmentByTag(mainTabType.tag)
        if (targetFragment == null) {
            targetFragment = getFragment(mainTabType)
            transaction.add(R.id.l_main_container, targetFragment, mainTabType.tag)
        }
        transaction.show(targetFragment)
        MainTabType.values()
            .filterNot { it == mainTabType }
            .forEach { type ->
                supportFragmentManager.findFragmentByTag(type.tag)?.let {
                    transaction.hide(it)
                }
            }
        transaction.commitAllowingStateLoss()
    }

    private fun drawProfileImage(url : String) {
        Glide.with(this).asBitmap().load(url).placeholder(R.drawable.ic_user).circleCrop()
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    binding.tbMain.menu.findItem(R.id.menu_profile).icon =
                        BitmapDrawable(resources, resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    binding.tbMain.menu.findItem(R.id.menu_profile).icon = placeholder
                }
            })
    }

}

enum class MainTabType(val tag: String, val id: Int) {
    ISSUE("Issue", R.id.tab_btn_issue),
    NOTIFICATION("Notification", R.id.tab_btn_notification)
}