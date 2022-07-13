package com.example.android_repo_05.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_05.R
import com.example.android_repo_05.data.model.LoginResponse
import com.example.android_repo_05.data.model.ResponseState
import com.example.android_repo_05.databinding.ActivityLoginBinding
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
            startActivity(Intent(Intent.ACTION_VIEW, this))
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
                is ResponseState.Success -> handleLoginSuccess(responseState)
                is ResponseState.Error -> handleLoginFailure()
                is ResponseState.Loading -> handleLoginLoading()
            }
        }
    }

    /*
        remote에서 access token을 성공적으로 받아 온 경우, 혹은 data store에 저장된 access token값을
        성공적으로 불러온 경우를 처리하는 메소드
        data store에서 access token 값을 불러온 경우 response.tokenType이 blanck로 설정됨
        즉, response.tokenType이 blanck가 아니면 remote에서 access token을 받아 온 것이기 때문에
        data store에 저장해야 함
    */
    private fun handleLoginSuccess(state: ResponseState<LoginResponse>) {
        binding.cpiLogin.visibility = View.INVISIBLE
        state.data?.let { response ->
            if (response.tokenType.isNotBlank()) {
                loginViewModel.setAccessTokenToDataStore(this, response.accessToken)
            }
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun handleLoginFailure() {
        binding.cpiLogin.visibility = View.INVISIBLE
        Snackbar.make(binding.root, R.string.login_login_failure, Snackbar.LENGTH_SHORT).show()
    }

    private fun handleLoginLoading() {
        binding.cpiLogin.visibility = View.VISIBLE
    }
}