package com.example.onexpress

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle

import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.onexpress.data.MyDatabase
import com.example.onexpress.data.entity.User
import com.example.onexpress.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val registerViewModel: RegisterViewModel by viewModels {
        val database = MyDatabase.getDatabase(applicationContext)
        val userDao = database.userDao()
        RegisterViewModelFactory(userDao)

    }

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.tvLogin.setOnClickListener {
            val intentLoginActivity = Intent(this, LoginActivity::class.java)
            startActivity(intentLoginActivity)
        }

        binding.btnRegister.setOnClickListener {

            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val password2 = binding.etPassword2.text.toString()

            if (email.isEmpty() || password.isEmpty() || password2.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                if (password != password2) {
                    Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
            registerViewModel.checkEmail(email)

            registerViewModel.isEmailUsed.observe(this, Observer { isUsed ->
                if (isUsed) {
                    Toast.makeText(this, "Email already used", Toast.LENGTH_SHORT)

                } else if (!isUsed) {
                    registerViewModel.registerUser(
                        User(
                            fullName = binding.etFullname.text.toString(),
                            username = binding.etEmail.text.toString(),
                            email = binding.etEmail.text.toString(),
                            password = binding.etPassword.text.toString()
                        )
                    )
                }
            })

            registerViewModel.registrationSuccess.observe(this, Observer { success ->
                if (success) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            })
        }
    }
}