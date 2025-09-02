/**
 * Prachi Tandel
 * Student ID: 2023ebcs178
 * LoginActivity - Handles user login with Firebase authentication
 * Saves username to SharedPreferences and navigates to WelcomeActivity
 */
package com.example.usermanagementapp_2023ebcs178

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth and SharedPreferences
        auth = FirebaseAuth.getInstance()
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        etUsername = findViewById(R.id.etLoginUsername)
        etPassword = findViewById(R.id.etLoginPassword)
        btnLogin = findViewById(R.id.btnLogin)
    }

    private fun setupClickListeners() {
        btnLogin.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Create email from username for Firebase Auth
        val email = "$username@usermanagement.com"

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Save username in SharedPreferences
                    val editor = sharedPreferences.edit()
                    editor.putString("username", username)
                    editor.putBoolean("isLoggedIn", true)
                    editor.apply()

                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

                    // Navigate to WelcomeActivity
                    val intent = Intent(this, WelcomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Login failed: ${task.exception?.message}",
                        Toast.LENGTH_LONG).show()
                }
            }
    }
}
