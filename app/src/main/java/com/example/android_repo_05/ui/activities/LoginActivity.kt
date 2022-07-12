package com.example.android_repo_05.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.android_repo_05.R
import com.example.android_repo_05.databinding.ActivityLoginBinding
import com.example.android_repo_05.others.Utils

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        initView()
        checkAccessCode()
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
        intent?.data?.getQueryParameter("code")?.run {
            Toast.makeText(this@LoginActivity, this, Toast.LENGTH_SHORT).show()
        }
    }
}