package com.example.android_repo_05.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_05.databinding.ActivityProfileBinding
import com.example.android_repo_05.repositories.TokenRepository
import com.example.android_repo_05.viewmodels.AppViewModelFactory
import com.example.android_repo_05.viewmodels.UserInfoViewModel

class ProfileActivity : AppCompatActivity() {
    private val binding by lazy { ActivityProfileBinding.inflate(layoutInflater) }
    private val userInfoViewModel by lazy {
        ViewModelProvider(
            this,
            AppViewModelFactory(githubApiRepository = TokenRepository.getInstance())
        )[UserInfoViewModel::class.java]
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
        binding.userInfoViewModel = userInfoViewModel
    }

    private fun setObservers() {
        userInfoViewModel.userInfo.observe(this) { responseState ->
            /*when (responseState) {
                is ResponseState.Success -> Toast.makeText(this, responseState.data?.displayName, Toast.LENGTH_SHORT).show()
                is ResponseState.Error -> Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                is ResponseState.Loading -> Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
            }*/
        }
    }

    private fun getUserInfo() {
        userInfoViewModel.getUserInfoFromRemote()
    }

}