package com.example.android_repo_05

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.example.android_repo_05.customview.MainTabButton
import com.example.android_repo_05.databinding.ActivityMainBinding
import com.example.android_repo_05.ui.IssueFragment
import com.example.android_repo_05.ui.NotificationFragment
import com.example.android_repo_05.ui.ProfileActivity
import com.example.android_repo_05.ui.SearchActivity
import com.example.android_repo_05.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
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

}

enum class MainTabType(val tag: String, val id: Int) {
    ISSUE("Issue", R.id.tab_btn_issue),
    NOTIFICATION("Notification", R.id.tab_btn_notification)
}