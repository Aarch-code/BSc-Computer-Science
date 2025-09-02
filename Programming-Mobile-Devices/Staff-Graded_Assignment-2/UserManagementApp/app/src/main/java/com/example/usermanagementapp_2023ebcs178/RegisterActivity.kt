/**
 * Prachi Tandel
 * Student ID: 2023ebcs178
 * RegisterActivity - Main entry point for user registration
 * Allows users to register with username, password, and role selection
 */
package com.example.usermanagementapp_2023ebcs178

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var spinnerRole: Spinner
    private lateinit var btnRegister: Button
    private lateinit var tvLoginLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        initializeViews()
        setupRoleSpinner()
        setupClickListeners()
    }

    private fun initializeViews() {
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        spinnerRole = findViewById(R.id.spinnerRole)
        btnRegister = findViewById(R.id.btnRegister)
        tvLoginLink = findViewById(R.id.tvLoginLink)
    }

    private fun setupRoleSpinner() {
        val roles = arrayOf("admin", "normal")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRole.adapter = adapter
    }

    private fun setupClickListeners() {
        btnRegister.setOnClickListener {
            registerUser()
        }

        tvLoginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun registerUser() {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val role = spinnerRole.selectedItem.toString()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isNetworkAvailable()) {
            showNetworkDialog()
            return
        }

        // Create email from username for Firebase Auth
        val email = "$username@usermanagement.com"

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Save user role in database
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        val userRef = database.reference.child("users").child(userId)
                        val userData = mapOf(
                            "username" to username,
                            "email" to email,
                            "role" to role
                        )
                        userRef.setValue(userData)
                    }

                    Toast.makeText(this, "User $username is registered", Toast.LENGTH_SHORT).show()
                    clearFields()
                } else {
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}",
                        Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

    private fun showNetworkDialog() {
        AlertDialog.Builder(this)
            .setTitle("No Internet Connection")
            .setMessage("Please check your internet connection and try again.")
            .setPositiveButton("Settings") { _, _ ->
                startActivity(Intent(android.provider.Settings.ACTION_WIFI_SETTINGS))
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun clearFields() {
        etUsername.setText("")
        etPassword.setText("")
        spinnerRole.setSelection(0)
    }
}
