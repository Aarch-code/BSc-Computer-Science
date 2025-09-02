/**
 * Prachi Tandel
 * Student ID: 2023ebcs178
 * MainActivity - Entry point that redirects to RegisterActivity
 */
package com.example.usermanagementapp_2023ebcs178

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Redirect to RegisterActivity as the main entry point
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}