package com.example.android_repo_05.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_05.R
import com.example.android_repo_05.data.models.ResponseState
import com.example.android_repo_05.data.models.TokenModel
import com.example.android_repo_05.databinding.ActivityLoginBinding
import com.example.android_repo_05.others.Utils
import com.example.android_repo_05.ui.viewmodels.AppViewModelFactory
import com.example.android_repo_05.ui.viewmodels.TokenViewModel
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val tokenViewModel by lazy {
        ViewModelProvider(this, AppViewModelFactory())[TokenViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        checkAccessCode()
        setObserver()
    }

    private fun initView() {
        binding.btnLogin.setOnClickListener {
            it.isClickable = false
            getGithubAccessCode()
        }
    }

    private fun getGithubAccessCode() {
        Utils.getGithubIdentityRequestUri().run {
            startActivity(Intent(Intent.ACTION_VIEW, this))
        }
    }

    private fun checkAccessCode() {
        intent?.data?.getQueryParameter("code")?.let { code ->
            tokenViewModel.getAccessTokenFromRemote(code)
        }
    }

    private fun setObserver() {
        tokenViewModel.tokenModel.observe(this) { responseState ->
            when (responseState) {
                is ResponseState.Success -> handleLoginSuccess(responseState)
                is ResponseState.Error -> handleLoginFailure()
                is ResponseState.Loading -> handleLoginLoading()
            }
        }
    }

    private fun handleLoginSuccess(state: ResponseState<TokenModel>) {
        binding.pbLogin.visibility = View.INVISIBLE
        state.data?.let { response ->
            if (!response.accessToken.isNullOrEmpty()) {
                tokenViewModel.setAccessTokenToDataStore(response.accessToken)
            }
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun handleLoginFailure() {
        binding.pbLogin.visibility = View.INVISIBLE
        binding.btnLogin.isClickable = true
        Toast.makeText(this, R.string.login_login_failure, Snackbar.LENGTH_SHORT).show()
    }

    private fun handleLoginLoading() {
        binding.pbLogin.visibility = View.VISIBLE
        binding.btnLogin.isClickable = false
    }
}