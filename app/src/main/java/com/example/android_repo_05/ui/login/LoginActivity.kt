package com.example.android_repo_05.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.android_repo_05.R
import com.example.android_repo_05.data.login.models.TokenModel
import com.example.android_repo_05.data.network.GithubApiInstance
import com.example.android_repo_05.data.network.ResponseState
import com.example.android_repo_05.databinding.ActivityLoginBinding
import com.example.android_repo_05.ui.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }


    private val tokenViewModel: TokenViewModel by viewModels()

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
        GithubApiInstance.getGithubIdentityRequestUri().run {
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