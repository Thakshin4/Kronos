package com.st10083983.kronos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Login
        val navHome = findViewById<Button>(R.id.confirm_login_button)

        navHome.setOnClickListener()
        {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
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
}