// Prachi Tandel : 2023ebcs178
package com.example.simplepersonalorganizer_2023ebcs178

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UserListActivity : AppCompatActivity() {

    private lateinit var usersRecyclerView: RecyclerView
    private lateinit var noDataTextView: TextView
    private lateinit var backButton: Button
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        // Initialize database helper
        databaseHelper = DatabaseHelper(this)

        // Initialize views
        initializeViews()

        // Setup RecyclerView
        setupRecyclerView()

        // Load and display admin users
        loadAdminUsers()

        // Set click listeners
        setupClickListeners()
    }

    private fun initializeViews() {
        usersRecyclerView = findViewById(R.id.usersRecyclerView)
        noDataTextView = findViewById(R.id.noDataTextView)
        backButton = findViewById(R.id.backButton)
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter()
        usersRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@UserListActivity)
            adapter = userAdapter
        }
    }

    private fun loadAdminUsers() {
        val adminUsers = databaseHelper.getAdminUsers()

        if (adminUsers.isEmpty()) {
            // Show no data message
            usersRecyclerView.visibility = View.GONE
            noDataTextView.visibility = View.VISIBLE
        } else {
            // Show users in RecyclerView
            usersRecyclerView.visibility = View.VISIBLE
            noDataTextView.visibility = View.GONE
            userAdapter.updateUsers(adminUsers)
        }
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            finish() // Go back to previous activity
        }
    }
}
