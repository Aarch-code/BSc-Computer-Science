/**
 * Prachi Tandel
 * Student ID: 2023ebcs178
 * WelcomeActivity - Main activity after login
 * Displays welcome message and provides data management functionality
 */
package com.example.usermanagementapp_2023ebcs178

import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class WelcomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var tvWelcome: TextView
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnSaveData: Button
    private lateinit var btnRetrieveData: Button
    private lateinit var btnLogout: Button
    private lateinit var lvUserData: ListView

    private var currentUsername: String = ""
    private var userRole: String = ""
    private val userDataList = mutableListOf<String>()
    private lateinit var dataAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Initialize Firebase and SharedPreferences
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        initializeViews()
        setupListView()
        loadUserData()
        setupClickListeners()

        // Start background service for periodic notifications
        startService(Intent(this, UserDataService::class.java))
    }

    private fun initializeViews() {
        tvWelcome = findViewById(R.id.tvWelcome)
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        btnSaveData = findViewById(R.id.btnSaveData)
        btnRetrieveData = findViewById(R.id.btnRetrieveData)
        btnLogout = findViewById(R.id.btnLogout)
        lvUserData = findViewById(R.id.lvUserData)
    }

    private fun setupListView() {
        dataAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, userDataList)
        lvUserData.adapter = dataAdapter
    }

    private fun loadUserData() {
        currentUsername = sharedPreferences.getString("username", "") ?: ""
        if (currentUsername.isNotEmpty()) {
            tvWelcome.text = "Welcome, $currentUsername!"

            // Get user role from Firebase
            val userId = auth.currentUser?.uid
            if (userId != null) {
                database.reference.child("users").child(userId).child("role")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            userRole = snapshot.getValue(String::class.java) ?: "normal"
                        }
                        override fun onCancelled(error: DatabaseError) {}
                    })
            }
        }
    }

    private fun setupClickListeners() {
        btnSaveData.setOnClickListener {
            saveUserData()
        }

        btnRetrieveData.setOnClickListener {
            retrieveUserData()
        }

        btnLogout.setOnClickListener {
            logoutUser()
        }
    }

    private fun saveUserData() {
        val name = etName.text.toString().trim()
        val email = etEmail.text.toString().trim()

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isNetworkAvailable()) {
            showNetworkDialog()
            return
        }

        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userDataRef = database.reference.child("userDetails").child(userId)
            val userData = mapOf(
                "name" to name,
                "email" to email,
                "username" to currentUsername,
                "timestamp" to System.currentTimeMillis()
            )

            userDataRef.setValue(userData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show()
                    clearFields()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun retrieveUserData() {
        if (!isNetworkAvailable()) {
            showNetworkDialog()
            return
        }

        // Re-fetch the user role to ensure we have the latest value
        val userId = auth.currentUser?.uid
        if (userId != null) {
            database.reference.child("users").child(userId).child("role")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        userRole = snapshot.getValue(String::class.java) ?: "normal"

                        // Now, with the correct role, decide which data to retrieve
                        userDataList.clear()
                        if (userRole == "admin") {
                            retrieveAllUserData()
                        } else {
                            retrieveCurrentUserData()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@WelcomeActivity, "Failed to get user role.", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    private fun retrieveAllUserData() {
        val userDataRef = database.reference.child("userDetails")
        userDataRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userDataList.clear()
                for (userSnapshot in snapshot.children) {
                    val name = userSnapshot.child("name").getValue(String::class.java) ?: ""
                    val email = userSnapshot.child("email").getValue(String::class.java) ?: ""
                    val username = userSnapshot.child("username").getValue(String::class.java) ?: ""

                    if (name.isNotEmpty() && email.isNotEmpty()) {
                        userDataList.add("User: $username\nName: $name\nEmail: $email\n")
                    }
                }
                dataAdapter.notifyDataSetChanged()

                if (userDataList.isEmpty()) {
                    Toast.makeText(this@WelcomeActivity, "No user data found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@WelcomeActivity, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun retrieveCurrentUserData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userDataRef = database.reference.child("userDetails").child(userId)
            userDataRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    userDataList.clear()
                    val name = snapshot.child("name").getValue(String::class.java) ?: ""
                    val email = snapshot.child("email").getValue(String::class.java) ?: ""

                    if (name.isNotEmpty() && email.isNotEmpty()) {
                        userDataList.add("Name: $name\nEmail: $email")
                    } else {
                        userDataList.add("No data found for current user")
                    }
                    dataAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@WelcomeActivity, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
                }
            })
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
        etName.setText("")
        etEmail.setText("")
    }

    private fun logoutUser() {
        // Clear SharedPreferences
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        // Sign out from Firebase
        auth.signOut()

        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()

        // Navigate back to RegisterActivity
        val intent = Intent(this, RegisterActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
