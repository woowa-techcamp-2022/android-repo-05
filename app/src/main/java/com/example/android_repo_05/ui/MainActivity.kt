package com.example.android_repo_05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.android_repo_05.databinding.ActivityMainBinding
import com.example.android_repo_05.ui.IssueFragment
import com.example.android_repo_05.ui.NotificationFragment
import com.example.android_repo_05.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        observe()
    }

    private fun initViews() {
        // TODO : 탭이 여러개인 경우에는 일일히 작성을 모두 해주어야 하므로, 확장성이 떨어짐. ViewGroup에서 SingleSelction 할 수 있는 방법 고민해보기
        binding.tabButtonIssue.setOnClickListener(this)
        binding.tabButtonNotification.setOnClickListener(this)
    }

    private fun observe() {
        mainViewModel.currentTabFragment.observe(this) {
            when(it) {
                MainTabType.ISSUE -> {
                    binding.tabButtonIssue.isChecked = true
                    binding.tabButtonNotification.isChecked = false
                }
                MainTabType.NOTIFICATION -> {
                    binding.tabButtonNotification.isChecked = true
                    binding.tabButtonIssue.isChecked = false
                }
            }
            changeFragment(it)
        }
    }

    override fun onClick(view: View) {
        mainViewModel.setCurrentTab(view.id)
    }

    private fun getFragment(mainTabType: MainTabType): Fragment =
        when(mainTabType) {
            MainTabType.ISSUE -> IssueFragment()
            MainTabType.NOTIFICATION -> NotificationFragment()
        }

    private fun changeFragment(mainTabType: MainTabType) {
        val transaction = supportFragmentManager.beginTransaction()
        var targetFragment = supportFragmentManager.findFragmentByTag(mainTabType.tag)
        if (targetFragment == null) {
            targetFragment = getFragment(mainTabType)
            transaction.add(R.id.main_container, targetFragment, mainTabType.tag)
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

enum class MainTabType(val tag : String, val id : Int) {
    ISSUE("Issue", R.id.tabButton_issue),
    NOTIFICATION("Notification", R.id.tabButton_notification)
}