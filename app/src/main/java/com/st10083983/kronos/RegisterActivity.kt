package com.st10083983.kronos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Register
        val navHome = findViewById<Button>(R.id.register_confirm_button)

        navHome.setOnClickListener()
        {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
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
}