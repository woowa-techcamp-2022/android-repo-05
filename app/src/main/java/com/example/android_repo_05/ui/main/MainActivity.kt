package com.example.android_repo_05.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.example.android_repo_05.R
import com.example.android_repo_05.ui.main.custom.MainTabButton
import com.example.android_repo_05.data.network.ResponseState
import com.example.android_repo_05.databinding.ActivityMainBinding
import com.example.android_repo_05.ui.profile.ProfileActivity
import com.example.android_repo_05.ui.main.notification.NotificationFragment
import com.example.android_repo_05.ui.main.issue.IssueFragment
import com.example.android_repo_05.ui.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getImageProfile()
        initViews()
        observe()
    }

    private fun initViews() {
        binding.lifecycleOwner = this
        binding.mainActivity = this

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
            if (responseState is ResponseState.Success) {
                binding.profileUrl = responseState.data!!.profileImageUrl
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

    private fun getImageProfile() {
        mainViewModel.getProfileUrlFromRemote()
    }
}

enum class MainTabType(val tag: String, val id: Int) {
    ISSUE("Issue", R.id.tab_btn_issue),
    NOTIFICATION("Notification", R.id.tab_btn_notification)
}