package com.st10083983.kronos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Variables
        val edtxUsername = findViewById<EditText>(R.id.login_username_input)
        val edtxPassword = findViewById<EditText>(R.id.login_password_input)

        // Login
        val navHome = findViewById<Button>(R.id.confirm_login_button)

        navHome.setOnClickListener()
        {
            val username = edtxUsername.text.toString()
            val password = edtxPassword.text.toString()

            handleLogin(username, password)
        }

        // Navigate to Register
        val navRegister = findViewById<Button>(R.id.nav_register_button)

        navRegister.setOnClickListener()
        {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun handleLogin(username: String, password: String)
    {
        // Debug Login
        mapExistingUsers["1Username"] = "1Password"

        // --- Login Logic Here ---
        if (mapExistingUsers.containsKey(username) && mapExistingUsers[username] == password)
        {
            // Navigate to Home
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        else
        {
            // Incorrect Details
            Toast.makeText(applicationContext,
                "Username or Password is incorrect." + mapExistingUsers["Name"] + password,
                Toast.LENGTH_SHORT).show()
        }
    }
}