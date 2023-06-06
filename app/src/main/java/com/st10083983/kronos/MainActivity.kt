package com.st10083983.kronos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myBt = findViewById<Button>(R.id.myButton)

        myBt.setOnClickListener()
        {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish()
        }
    }
}