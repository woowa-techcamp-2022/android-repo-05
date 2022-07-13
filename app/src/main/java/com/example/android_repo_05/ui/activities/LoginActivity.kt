package com.example.android_repo_05.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_05.R
import com.example.android_repo_05.databinding.ActivityLoginBinding
import com.example.android_repo_05.model.ResponseState
import com.example.android_repo_05.others.Utils
import com.example.android_repo_05.repositories.GithubApiRepository
import com.example.android_repo_05.viewmodels.LoginViewModel
import com.example.android_repo_05.viewmodels.LoginViewModelFactory
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel by lazy {
        ViewModelProvider(
            this,
            LoginViewModelFactory(GithubApiRepository.getInstance())
        )[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        initView()
        checkAccessCode()
        setObserver()
    }

    private fun initView() {
        binding.btnLogin.setOnClickListener {
            getGithubAccessCode()
        }
    }

    /*
       사용자의 Github identity 요청 메소드
    */
    private fun getGithubAccessCode() {
        Utils.getGithubIdentityRequestUri().run {
            startActivity(Intent(Intent.ACTION_VIEW, this).apply {
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            })
        }
    }

    /*
       getGithubAccessCode() 메소드 호출로 사용자의 Github identity 요청 작업을 완료하면
       액티비티가 재실행 되면서 실행하게 로직을 담당하는 메소드
       access code를 획득하고 accessToken 획득 작업을 실행함
     */
    private fun checkAccessCode() {
        intent?.data?.getQueryParameter("code")?.let { code ->
            loginViewModel.getAccessTokenFromRemote(code)
        }
    }

    private fun setObserver() {
        loginViewModel.loginResponse.observe(this) { responseState ->
            when (responseState) {
                is ResponseState.Success -> {
                    Snackbar.make(this, binding.root, "login success", Snackbar.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                }
                is ResponseState.Error -> {
                    Snackbar.make(this, binding.root, "login failed", Snackbar.LENGTH_SHORT).show()
                }
                is ResponseState.Loading -> {
                    Snackbar.make(this, binding.root, "loading...", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}