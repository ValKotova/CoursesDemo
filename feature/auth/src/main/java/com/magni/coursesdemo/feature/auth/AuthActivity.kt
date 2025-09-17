package com.magni.coursesdemo.feature.auth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.magni.coursesdemo.feature.auth.databinding.ActivityAuthBinding
import com.magni.coursesdemo.presentation.NavigationContract
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private val viewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var navigator: NavigationContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.loginSuccess.observe(this) { success ->
            if (success) {
                navigator.navigateToHome(this)
                finish()
            }
        }

        viewModel.isLoginEnabled.observe(this) { isEnabled ->
            binding.btnLogin.isEnabled = isEnabled
        }
    }

    private fun setupListeners() {
        binding.etLogin.doAfterTextChanged { text ->
            viewModel.onLoginChanged(text?.toString() ?: "")
        }

        binding.etPassword.doAfterTextChanged { text ->
            viewModel.onPasswordChanged(text?.toString() ?: "")
        }

        binding.btnLogin.setOnClickListener {
            viewModel.login()
        }
    }
}