package com.example.android_repo_05.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_05.data.models.ResponseState
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
        userViewModel.userData.observe(this) { state ->
            when (state) {
                is ResponseState.Success -> handleSuccess()
                is ResponseState.Error -> handleError()
                is ResponseState.Loading -> handleLoading()
            }
        }
    }

    private fun getUserInfo() {
        userViewModel.getUserInfoFromRemote()
        userViewModel.getUserStarredFromRemote()
    }

    private fun handleSuccess() {
        binding.lProfileSuccess.visibility = View.VISIBLE
        binding.pbProfile.visibility = View.GONE
        binding.lProfileError.visibility = View.GONE
    }

    private fun handleError() {
        binding.lProfileSuccess.visibility = View.GONE
        binding.pbProfile.visibility = View.GONE
        binding.lProfileError.visibility = View.VISIBLE
    }

    private fun handleLoading() {
        binding.lProfileSuccess.visibility = View.GONE
        binding.pbProfile.visibility = View.VISIBLE
        binding.lProfileError.visibility = View.GONE
    }
}