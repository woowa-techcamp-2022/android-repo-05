package com.example.android_repo_05.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_05.databinding.ActivityProfileBinding
import com.example.android_repo_05.ui.common.AppViewModelFactory

class ProfileActivity : AppCompatActivity() {
    private val binding by lazy { ActivityProfileBinding.inflate(layoutInflater) }
    private val userViewModel by lazy {
        ViewModelProvider(this, AppViewModelFactory())[UserViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
        setBindingProperty()
        setObservers()
        getUserInfo()
    }

    private fun initViews() {
        binding.tbProfile.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setBindingProperty() {
        binding.lifecycleOwner = this
        binding.userViewModel = userViewModel
    }

    private fun setObservers() {
        userViewModel.userData.observe(this) { state ->
            state?.let {
                binding.responseState = it
            }
        }
    }

    private fun getUserInfo() {
        userViewModel.getUserInfoFromRemote()
        userViewModel.getUserStarredFromRemote()
    }
}