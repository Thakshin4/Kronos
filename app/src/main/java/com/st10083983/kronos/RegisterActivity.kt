package com.st10083983.kronos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

val mapExistingUsers = hashMapOf<String, String>()

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Variables
        val username = findViewById<EditText>(R.id.register_username_input).toString()
        val password = findViewById<EditText>(R.id.register_password_input).toString()

        // Register
        val navHome = findViewById<Button>(R.id.register_confirm_button)

        navHome.setOnClickListener()
        {
            handleRegister(username, password)
        }

        // Navigate to Login
        val navLogin = findViewById<Button>(R.id.nav_login_button)

        navLogin.setOnClickListener()
        {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Handle User Registration
    private fun handleRegister(username: String, password: String)
    {
        // --- Register Logic Here ---
        if (mapExistingUsers.contains(username))
        {
            // Existing User
            Toast.makeText(applicationContext,
                "User already exists.",
                Toast.LENGTH_SHORT).show()
        }
        else
        {
            // Add New User
            val newUser = username to password
            mapExistingUsers[newUser.first] = newUser.second

            // Navigate to Login
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}