package com.example.onexpress

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.onexpress.data.MyDatabase
import com.example.onexpress.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout with view binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup ViewModel
        val database = MyDatabase.getDatabase(applicationContext)
        val userDao = database.userDao()
        val factory = LoginViewModelFactory(userDao)
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        // Button login listener
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            loginViewModel.auth(email, password)
        }

        // Register button listener
        binding.tvRegister.setOnClickListener {
            val regIntent = Intent(this, RegisterActivity::class.java)
            startActivity(regIntent)
        }

        // Observe authentication success
        loginViewModel.authSuccess.observe(this, Observer { success ->
            if (success) {
                // Save login status to SharedPreferences
                val sharedPref = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putBoolean("isLoggedIn", true)
                    apply()
                }

                // Navigate to MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Close LoginActivity
            } else {
                Toast.makeText(this, "Invalid Account", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
