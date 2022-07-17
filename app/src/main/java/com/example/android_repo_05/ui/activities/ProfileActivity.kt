package com.example.android_repo_05.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_05.databinding.ActivityProfileBinding
import com.example.android_repo_05.ui.viewmodels.AppViewModelFactory
import com.example.android_repo_05.ui.viewmodels.UserViewModel

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
        userViewModel.userModel.observe(this) { responseState ->
            /*when (responseState) {
                is ResponseState.Success -> Toast.makeText(this, responseState.data?.displayName, Toast.LENGTH_SHORT).show()
                is ResponseState.Error -> Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                is ResponseState.Loading -> Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
            }*/
        }
        userViewModel.starredCount.observe(this) { responseState ->
            /*when (responseState) {
                is ResponseState.Success -> Toast.makeText(this, responseState.data, Toast.LENGTH_SHORT).show()
                is ResponseState.Error -> Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                is ResponseState.Loading -> Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
            }*/
        }
    }

    private fun getUserInfo() {
        userViewModel.getUserInfoFromRemote()
        userViewModel.getUserStarredFromRemote()
    }

}