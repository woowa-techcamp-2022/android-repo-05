package com.example.android_repo_05.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_repo_05.MainTabType
import com.example.android_repo_05.R

class MainViewModel : ViewModel() {
    private val _currentTabFragment = MutableLiveData(MainTabType.ISSUE)
    val currentTabFragment : LiveData<MainTabType> = _currentTabFragment

    private fun getMainTabType(itemId : Int) : MainTabType =
        when(itemId) {
            R.id.tab_btn_issue -> MainTabType.ISSUE
            R.id.tab_btn_notification -> MainTabType.NOTIFICATION
            else -> throw IllegalArgumentException()
        }

    fun setCurrentTab(itemId : Int) : Boolean {
        changeCurrentTab(getMainTabType(itemId))
        return true
    }

    private fun changeCurrentTab(mainTabType : MainTabType) {
        if (currentTabFragment.value != mainTabType) _currentTabFragment.value = mainTabType
    }

}