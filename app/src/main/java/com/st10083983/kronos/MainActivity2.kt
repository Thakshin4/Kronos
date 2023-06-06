package com.st10083983.kronos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity2 : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val myOtherBt = findViewById<Button>(R.id.myOtherButton)

        myOtherBt.setOnClickListener()
        {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
            finish()
        }
    }
}