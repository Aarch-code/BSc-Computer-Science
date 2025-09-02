// Prachi Tandel : 2023ebcs178
package com.example.simplepersonalorganizer_2023ebcs178

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var viewUsersButton: Button
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize database helper
        databaseHelper = DatabaseHelper(this)

        // Initialize views
        initializeViews()

        // Set click listeners
        setupClickListeners()
    }

    private fun initializeViews() {
        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        saveButton = findViewById(R.id.saveButton)
        viewUsersButton = findViewById(R.id.viewUsersButton)
    }

    private fun setupClickListeners() {
        saveButton.setOnClickListener {
            saveUserData()
        }

        viewUsersButton.setOnClickListener {
            navigateToUserList()
        }
    }

    private fun saveUserData() {
        val name = nameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()

        // Validate input fields
        if (name.isEmpty()) {
            nameEditText.error = "Name is required"
            nameEditText.requestFocus()
            return
        }

        if (email.isEmpty()) {
            emailEditText.error = "Email is required"
            emailEditText.requestFocus()
            return
        }

        if (!isValidEmail(email)) {
            emailEditText.error = "Please enter a valid email"
            emailEditText.requestFocus()
            return
        }

        // Save data to database
        val success = databaseHelper.insertUser(name, email)

        if (success) {
            Toast.makeText(this, "User data saved successfully!", Toast.LENGTH_SHORT).show()
            clearFields()
        } else {
            Toast.makeText(this, "Failed to save user data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun clearFields() {
        nameEditText.text.clear()
        emailEditText.text.clear()
    }

    private fun navigateToUserList() {
        val intent = Intent(this, UserListActivity::class.java)
        startActivity(intent)
    }
}